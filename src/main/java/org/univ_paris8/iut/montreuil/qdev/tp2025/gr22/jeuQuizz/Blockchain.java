package org.univ_paris8.iut.montreuil.qdev.tp2025.gr22.jeuQuizz;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Blockchain {
    private final List<Block> chain;
    private ConsensusAlgorithm consensus;

    public Blockchain() {
        chain = new ArrayList<>();
        chain.add(new Block(0, "GENESIS", "N/A", "INIT", "SYSTEM", "0"));
    }

    public Blockchain(ConsensusAlgorithm consensus) {
        this();
        this.consensus = consensus;
        System.out.println("Consensus utilisé : " + consensus.getName());
    }

    public void setConsensus(ConsensusAlgorithm consensus) {
        this.consensus = consensus;
    }

    public void addBlock(String eventId, String artist, String status, String owner) {
        Block lastBlock = chain.get(chain.size() - 1);
        Block newBlock = new Block(chain.size(), eventId, artist, status, owner, lastBlock.hash);

        if (consensus != null) {
            consensus.validate(newBlock);
        }

        chain.add(newBlock);
    }

    public boolean isChainValid() {
        for (int i = 1; i < chain.size(); i++) {
            Block current = chain.get(i);
            Block previous = chain.get(i - 1);

            if (!current.hash.equals(current.calculateHash())) {
                return false;
            }

            if (!current.previousHash.equals(previous.hash)) {
                return false;
            }
        }
        return true;
    }

    public void exportAsJson(String filePath) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(chain, writer);
            System.out.println("Blockchain exportée dans " + filePath);
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de l'export JSON", e);
        }
    }

    public String getLastStatus() {
        return chain.get(chain.size() - 1).status;
    }

    public void displayChain() {
        for (Block block : chain) {
            System.out.println("Index        : " + block.index);
            System.out.println("Horodatage   : " + block.timestamp);
            System.out.println("Évènement    : " + block.eventId);
            System.out.println("Artiste      : " + block.artist);
            System.out.println("Statut       : " + block.status);
            System.out.println("Propriétaire : " + block.owner);
            System.out.println("Nonce        : " + block.nonce);
            System.out.println("Hash préc.   : " + block.previousHash);
            System.out.println("Hash         : " + block.hash);
            System.out.println("-------------------------------------");
        }
    }
}
