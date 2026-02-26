package org.univ_paris8.iut.montreuil.qdev.tp2025.gr22.jeuQuizz;

public class ProofOfWork implements ConsensusAlgorithm {
    private final int difficulty;

    public ProofOfWork(int difficulty) {
        this.difficulty = difficulty;
    }

    @Override
    public void validate(Block block) {
        String target = "0".repeat(difficulty);
        while (!block.hash.startsWith(target)) {
            block.nonce++;
            block.hash = block.calculateHash();
        }
        System.out.println("[PoW] Bloc miné (nonce=" + block.nonce + ") : " + block.hash);
    }

    @Override
    public String getName() {
        return "Proof of Work (difficulté=" + difficulty + ")";
    }
}
