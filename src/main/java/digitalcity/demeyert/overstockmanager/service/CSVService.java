package digitalcity.demeyert.overstockmanager.service;

import digitalcity.demeyert.overstockmanager.exception.ElementNotFoundException;
import digitalcity.demeyert.overstockmanager.helper.CSVHelper;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import digitalcity.demeyert.overstockmanager.mapper.CardMapper;
import digitalcity.demeyert.overstockmanager.model.dto.CardDTO;
import digitalcity.demeyert.overstockmanager.model.entity.*;
import digitalcity.demeyert.overstockmanager.repository.CardRepository;
import digitalcity.demeyert.overstockmanager.repository.CollecRepository;
import digitalcity.demeyert.overstockmanager.repository.CollectCardRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;

@Service
@Transactional(dontRollbackOn = { RuntimeException.class })
public class CSVService {
    private final CardRepository repository;
    private final CardMapper mapper;
    private final CollecRepository collecRepository;

    private final CollectCardRepository collectCardRepository;

    public CSVService(CardRepository repository, CardMapper mapper, CollecRepository collecRepository, CollectCardRepository collectCardRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.collecRepository = collecRepository;
        this.collectCardRepository = collectCardRepository;
    }

    public void save(MultipartFile file, Long id) {
        try {
            List<CardDTO> cardsDto = CSVHelper.csvToCards(file.getInputStream());

            List<Card> cards = cardsDto.stream()
                    .map(mapper::toEntities)
                    .map(this::findAllReadyInDataBase)
                    .map(repository::save)
                    .collect(Collectors.toList());


            Collec collec = collecRepository.findById(id).orElseThrow(() -> new ElementNotFoundException(Card.class, id));

            cards.forEach((card) -> {
                collectCardRepository.findById(new CollectCardId(collec.getId(), card.getId()))
                        .ifPresentOrElse(
                                (presence) -> {
                                    presence.setQtt(presence.getQtt() + card.getCount());
                                    collectCardRepository.save(presence);
                                },
                                () -> {
                                    CollectCard cc = new CollectCard();
                                    cc.setCard(card);
                                    cc.setCollec(collec);
                                    cc.setQtt(card.getCount());
                                    collectCardRepository.save(cc);
                                }
                        );
            });
        }catch (IOException e) {
            throw new RuntimeException("fail to store csv data: " + e.getMessage());
        }

    }
    private Card findAllReadyInDataBase( Card toFind ){
        Card found = repository.findByCardmarketIdAndFoilAndSignedAndStateAndLanguage(
                toFind.getCardmarketId(),
                toFind.isFoil(),
                toFind.isSigned(),
                toFind.getState(),
                toFind.getLanguage()
        );

        if( found != null  ) {
            found.setCount( found.getCount() + toFind.getCount() );
            return found;
        }
        else {
            return toFind;
        }
    }
}
