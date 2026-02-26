package org.univ_paris8.iut.montreuil.qdev.tp2025.gr22.jeuQuizz;

import java.security.MessageDigest;
import java.time.Instant;

public class Block {
    public int index;
    public String timestamp, previousHash, hash;
    public String eventId;
    public String artist;
    public String status;
    public String owner;
    public int nonce;

    public Block(int index, String eventId, String artist, String status, String owner, String previousHash) {
        this.index = index;
        this.timestamp = Instant.now().toString();
        this.eventId = eventId;
        this.artist = artist;
        this.status = status;
        this.owner = owner;
        this.previousHash = previousHash;
        this.nonce = 0;
        this.hash = calculateHash();
    }

    public String calculateHash() {
        try {
            String input = index + timestamp + eventId + artist + status + owner + previousHash + nonce;
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(input.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) hexString.append(String.format("%02x", b));
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
