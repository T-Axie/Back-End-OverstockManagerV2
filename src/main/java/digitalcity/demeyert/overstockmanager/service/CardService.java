package digitalcity.demeyert.overstockmanager.service;

import digitalcity.demeyert.overstockmanager.exception.ElementNotFoundException;
import digitalcity.demeyert.overstockmanager.mapper.CardMapper;
import digitalcity.demeyert.overstockmanager.model.dto.CardDTO;
import digitalcity.demeyert.overstockmanager.model.entity.Card;
import digitalcity.demeyert.overstockmanager.model.entity.Rarity;
import digitalcity.demeyert.overstockmanager.repository.CardRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CardService {

    private final CardRepository repository;
    private final CardMapper mapper;



    public CardService(CardRepository repository, CardMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public CardDTO cardByName (String name) {
        if( name == null)
            throw new IllegalArgumentException("inserted name cannot be empty");
        return mapper.fromEntities(repository.findCardsByName(name));
    }

    public List<CardDTO> cardBySet (String name) {
        if( name == null)
            throw new IllegalArgumentException("inserted set cannot be empty");
        return repository.findCardsByGameSet(name).stream().map(mapper::fromEntities).collect(Collectors.toList());
    }

    public CardDTO delete(long id) {
        Card card = repository.findById(id).orElseThrow(() -> new ElementNotFoundException(Card.class, id));
        repository.delete(card);
        card.setId(null);
        return mapper.fromEntities(card);
    }

    public CardDTO getOne(long id) {
        return repository.findById(id).map(mapper::fromEntities).orElseThrow(() -> new ElementNotFoundException(Card.class, id));
    }

    public List<CardDTO> cardByrarity(Rarity rarity) {
        return repository.findCardsByRarity(rarity).stream().map(mapper::fromEntities).collect(Collectors.toList());
    }
    public List<CardDTO> getAll () {
        return repository.findAll().stream().map(mapper::fromEntities).collect(Collectors.toList());
    }

    public CardDTO create(CardDTO cardDTO) {
        Card card = mapper.toEntities(cardDTO);
        return mapper.fromEntities(repository.save(card));
    }

    public CardDTO modify(CardDTO cardDTO) {
        Card cardToUpdate = mapper.toEntities(getOne(cardDTO.getId()));

        if (cardDTO.getCardmarketId() != 0)
            cardToUpdate.setCardmarketId(cardDTO.getCardmarketId());
        if (cardDTO.getCount() != 0)
            cardToUpdate.setCount(cardDTO.getCount());
        if (cardDTO.getComment() != null)
            cardToUpdate.setComment(cardDTO.getComment());
        if (cardDTO.getGameSet() != null)
            cardToUpdate.setGameSet(cardDTO.getGameSet());
        if (cardDTO.getState() != null)
            cardToUpdate.setState(cardDTO.getState());
        if (cardDTO.getRarity() != null)
            cardToUpdate.setRarity(cardDTO.getRarity());
        if (cardDTO.getLanguage() != null)
            cardToUpdate.setLanguage(cardDTO.getLanguage());
        if (cardDTO.getName() != null)
            cardToUpdate.setName(cardDTO.getName());
        if (cardDTO.getFoil() != null)
            cardToUpdate.setFoil(cardDTO.getFoil());
        if (cardDTO.getPlayset() != null)
            cardToUpdate.setPlayset(cardDTO.getPlayset());
        if (cardDTO.getSigned() != null)
            cardToUpdate.setPlayset(cardDTO.getPlayset());

        return mapper.fromEntities(repository.save(cardToUpdate));
    }
}