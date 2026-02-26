package org.univ_paris8.iut.montreuil.qdev.tp2025.gr22.jeuQuizz;

import java.util.HashSet;
import java.util.Set;

public class ProofOfAuthority implements ConsensusAlgorithm {
    private final Set<String> authorities = new HashSet<>();
    private int turnIndex = 0;

    public void addAuthority(String authority) {
        authorities.add(authority);
    }

    @Override
    public void validate(Block block) {
        if (authorities.isEmpty()) {
            throw new IllegalStateException("Aucune autorité enregistrée");
        }

        String[] authorityArray = authorities.toArray(new String[0]);
        String current = authorityArray[turnIndex % authorityArray.length];
        turnIndex++;

        System.out.println("[PoA] Bloc validé par l'autorité : " + current);
    }

    @Override
    public String getName() {
        return "Proof of Authority";
    }
}
