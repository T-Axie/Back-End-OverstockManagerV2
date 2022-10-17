package digitalcity.demeyert.overstockmanager.service;

import digitalcity.demeyert.overstockmanager.exception.ElementNotFoundException;
import digitalcity.demeyert.overstockmanager.mapper.CollecMapper;
import digitalcity.demeyert.overstockmanager.model.dto.CollecDTO;
import digitalcity.demeyert.overstockmanager.model.entity.Card;
import digitalcity.demeyert.overstockmanager.model.entity.Collec;
import digitalcity.demeyert.overstockmanager.model.entity.CollectCard;
import digitalcity.demeyert.overstockmanager.model.entity.Users;
import digitalcity.demeyert.overstockmanager.model.forms.CollecCreateForm;
import digitalcity.demeyert.overstockmanager.repository.CollecRepository;
import digitalcity.demeyert.overstockmanager.repository.CollectCardRepository;
import digitalcity.demeyert.overstockmanager.repository.UserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CollectService{
    private final CollecRepository repository;
    private final CollecMapper mapper;
    private final UserRepository userRepository;
    private final CollectCardRepository collectCardRepository;

    public CollectService(CollecRepository repository, CollecMapper mapper, UserRepository userRepository, CollectCardRepository collectCardRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.userRepository = userRepository;
        this.collectCardRepository = collectCardRepository;
    }

    public CollecDTO create(UsernamePasswordAuthenticationToken auth, CollecCreateForm form) {
        if( form == null)
            throw new IllegalArgumentException("inserted collection cannot be null");

        Users usersToUpdate = userRepository.findByEmail(auth.getPrincipal().toString())
                .orElseThrow(() -> new ElementNotFoundException(Card.class, auth.getPrincipal().toString()));
        Collec collec = mapper.toEntities(form);

        usersToUpdate.setOneCollec(collec);
        userRepository.save(usersToUpdate);

        return mapper.fromEntities(collec);
    }

    public CollecDTO update(Long id, CollecDTO collecDTO) {
        return null;
    }

    public CollecDTO getOne(Long id) {
        return repository.findById(id).map(mapper::fromEntities).orElseThrow(() -> new ElementNotFoundException(Collec.class, id));
    }

    public List<CollecDTO> getAll() {
        return repository.findAll().stream().map(mapper::fromEntities).collect(Collectors.toList());
    }

    public String delete(UsernamePasswordAuthenticationToken auth, Long id) {
        Users usersToUpdate = userRepository.findByEmail(auth.getPrincipal().toString())
                .orElseThrow(() -> new ElementNotFoundException(Card.class, auth.getPrincipal().toString()));

        Collec collec = repository.findById(id).orElseThrow(() -> new ElementNotFoundException(Collec.class, id));
        CollectCard collectCard = collectCardRepository.findByCollec_Id(id);
        collectCard.setCollec(null);
        collectCardRepository.save(collectCard);
        usersToUpdate.removeOneCollec(collec);
        userRepository.save(usersToUpdate);
        repository.deleteById(id);
        collec.setId(null);
        return "Your collection is successfully removed";
     }
}
