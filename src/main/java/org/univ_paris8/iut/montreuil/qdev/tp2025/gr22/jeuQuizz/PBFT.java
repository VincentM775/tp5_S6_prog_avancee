package org.univ_paris8.iut.montreuil.qdev.tp2025.gr22.jeuQuizz;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PBFT implements ConsensusAlgorithm {
    private final List<String> nodes = new ArrayList<>();
    private final Random random = new Random();

    public void addNode(String nodeName) {
        nodes.add(nodeName);
    }

    @Override
    public void validate(Block block) {
        if (nodes.isEmpty()) {
            throw new IllegalStateException("Aucun noeud enregistré");
        }

        int totalNodes = nodes.size();
        int maxFaulty = (totalNodes - 1) / 3;
        int required = 2 * maxFaulty + 1;

        System.out.println("[PBFT] Noeuds : " + totalNodes
                + " | Tolérance pannes : " + maxFaulty
                + " | Votes requis : " + required);

        // Phase 1 : Pre-prepare (le leader propose le bloc)
        String leader = nodes.get(0);
        System.out.println("[PBFT] Phase PRE-PREPARE : leader=" + leader);

        // Phase 2 : Prepare (chaque noeud vote)
        int votes = 0;
        for (String node : nodes) {
            boolean honest = random.nextInt(100) < 80; // 80% de chance d'être honnête
            if (honest) {
                votes++;
                System.out.println("[PBFT] Phase PREPARE : " + node + " -> OK");
            } else {
                System.out.println("[PBFT] Phase PREPARE : " + node + " -> REJET (byzantin)");
            }
        }

        // Phase 3 : Commit
        if (votes >= required) {
            System.out.println("[PBFT] Phase COMMIT : consensus atteint (" + votes + "/" + totalNodes + ")");
        } else {
            System.out.println("[PBFT] ECHEC : pas assez de votes (" + votes + "/" + required + " requis)");
        }
    }

    @Override
    public String getName() {
        return "PBFT (Practical Byzantine Fault Tolerance)";
    }
}
