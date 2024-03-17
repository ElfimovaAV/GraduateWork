package ru.gb.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.backend.models.Rules;
import ru.gb.backend.repository.RulesRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RuleService {
    private final RulesRepository rulesRepository;
    /**
     * Метод для получения списка всех правил игр
     * @return все правила игр
     */
    public List<Rules> getAllRules() {
        return rulesRepository.findAll();
    }

    /**
     * Метод для добавления правил игры в БД
     * @param rules
     * @return сохраненные правила игры
     */
    public Rules createRules(Rules rules) {
        return rulesRepository.save(rules);
    }

    /**
     * Метод для получения правил игры по id
     * @param id
     * @return правила игры с заданным id или null
     */
    public Rules getRulesById(Long id) {
        return rulesRepository.findById(id).orElseThrow(null);
    }

    /**
     * Метод для редактирования правил игры
     * @param rules
     * @return отредактированные правила игры
     */
    public Rules updateRules(Long id, Rules rules) {
        Optional<Rules> optionalRules = rulesRepository.findById(id);
        if (optionalRules.isPresent()) {
            Rules rulesById = optionalRules.get();
            rulesById.setRules(rules.getRules());
            return rulesRepository.save(rulesById);
        }  else {
            throw new IllegalArgumentException("Rules not found with id: " + id);
        }
    }

    /**
     * Метод для удаления правил игры
     * @param id
     */
    public void deleteRules(Long id) {
        Rules rulesById = getRulesById(id);
        rulesRepository.delete(rulesById);
    }
}
