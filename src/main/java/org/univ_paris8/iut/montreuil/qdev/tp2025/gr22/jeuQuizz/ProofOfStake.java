package org.univ_paris8.iut.montreuil.qdev.tp2025.gr22.jeuQuizz;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ProofOfStake implements ConsensusAlgorithm {
    private final Map<String, Integer> stakes = new HashMap<>();
    private final Random random = new Random();

    public void addStake(String validator, int amount) {
        stakes.merge(validator, amount, Integer::sum);
    }

    @Override
    public void validate(Block block) {
        String selected = selectValidator();
        System.out.println("[PoS] Validateur sélectionné : " + selected
                + " (stake=" + stakes.get(selected) + ")");
    }

    private String selectValidator() {
        int totalStake = stakes.values().stream().mapToInt(Integer::intValue).sum();
        if (totalStake == 0) {
            throw new IllegalStateException("Aucun validateur enregistré");
        }
        int ticket = random.nextInt(totalStake);
        int cumul = 0;
        for (Map.Entry<String, Integer> entry : stakes.entrySet()) {
            cumul += entry.getValue();
            if (ticket < cumul) {
                return entry.getKey();
            }
        }
        return stakes.keySet().iterator().next();
    }

    @Override
    public String getName() {
        return "Proof of Stake";
    }
}
