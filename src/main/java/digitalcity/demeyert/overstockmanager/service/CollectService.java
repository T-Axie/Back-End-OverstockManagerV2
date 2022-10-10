package digitalcity.demeyert.overstockmanager.service;

import digitalcity.demeyert.overstockmanager.exception.ElementNotFoundException;
import digitalcity.demeyert.overstockmanager.mapper.CollecMapper;
import digitalcity.demeyert.overstockmanager.model.dto.CollecDTO;
import digitalcity.demeyert.overstockmanager.model.entity.Collec;
import digitalcity.demeyert.overstockmanager.model.forms.CollecCreateForm;
import digitalcity.demeyert.overstockmanager.repository.CollecRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CollectService{
    private final CollecRepository repository;
    private final CollecMapper mapper;

    public CollectService(CollecRepository repository, CollecMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public CollecDTO create(CollecCreateForm form) {
        if( form == null)
            throw new IllegalArgumentException("inserted child cannot be null");
        return mapper.fromEntities(repository.save(mapper.toEntrities(form)));
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

    public CollecDTO delete(Long id) {
        Collec collec = repository.findById(id).orElseThrow(() -> new ElementNotFoundException(Collec.class, id));
        repository.delete(collec);
        collec.setId(null);
        return mapper.fromEntities(collec);
     }
}
