package org.univ_paris8.iut.montreuil.qdev.tp2025.gr22.jeuQuizz;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("  DEMO DES MECANISMES DE CONSENSUS");
        System.out.println("========================================\n");

        demoProofOfWork();
        demoProofOfStake();
        demoPBFT();
        demoProofOfAuthority();

        System.out.println("\n========================================");
        System.out.println("  SIMULATION WORKFLOW TICKET");
        System.out.println("========================================\n");
        simulateTicketWorkflow();

        System.out.println("\n=== API Console ===\n");
        apiConsole();
    }

    private static void demoProofOfWork() {
        System.out.println("--- Proof of Work (difficulté=3) ---");
        ProofOfWork pow = new ProofOfWork(7);
        Blockchain bc = new Blockchain(pow);
        bc.addBlock("EVT-POW", "Daft Punk", "ACHETE", "Alice");
        System.out.println("Chaîne valide : " + bc.isChainValid());
        System.out.println();
    }

    private static void demoProofOfStake() {
        System.out.println("--- Proof of Stake ---");
        ProofOfStake pos = new ProofOfStake();
        pos.addStake("Alice", 50);
        pos.addStake("Bob", 30);
        pos.addStake("Charlie", 20);
        Blockchain bc = new Blockchain(pos);
        bc.addBlock("EVT-POS", "Stromae", "ACHETE", "Bob");
        System.out.println("Chaîne valide : " + bc.isChainValid());
        System.out.println();
    }

    private static void demoPBFT() {
        System.out.println("--- PBFT ---");
        PBFT pbft = new PBFT();
        pbft.addNode("Noeud-A");
        pbft.addNode("Noeud-B");
        pbft.addNode("Noeud-C");
        pbft.addNode("Noeud-D");
        Blockchain bc = new Blockchain(pbft);
        bc.addBlock("EVT-PBFT", "Aya Nakamura", "ACHETE", "Charlie");
        System.out.println("Chaîne valide : " + bc.isChainValid());
        System.out.println();
    }

    private static void demoProofOfAuthority() {
        System.out.println("--- Proof of Authority ---");
        ProofOfAuthority poa = new ProofOfAuthority();
        poa.addAuthority("Autorité-Gouv");
        poa.addAuthority("Autorité-Banque");
        Blockchain bc = new Blockchain(poa);
        bc.addBlock("EVT-POA-1", "Jul", "ACHETE", "Alice");
        bc.addBlock("EVT-POA-2", "Jul", "REVENDU", "Bob");
        System.out.println("Chaîne valide : " + bc.isChainValid());
        System.out.println();
    }

    private static void simulateTicketWorkflow() {
        ProofOfWork pow = new ProofOfWork(2);
        Blockchain blockchain = new Blockchain(pow);

        blockchain.addBlock("EVT-001", "Beyoncé", "ACHETE", "Alice");
        System.out.println("[1] Alice achète un ticket pour le concert de Beyoncé");

        blockchain.addBlock("EVT-001", "Beyoncé", "REVENDU", "Bob");
        System.out.println("[2] Alice revend le ticket à Bob");

        blockchain.addBlock("EVT-001", "Beyoncé", "REVENDU", "Charlie");
        System.out.println("[3] Bob revend le ticket à Charlie");

        blockchain.addBlock("EVT-001", "Beyoncé", "UTILISE", "Charlie");
        System.out.println("[4] Charlie utilise le ticket pour entrer au concert");

        blockchain.addBlock("EVT-001", "Beyoncé", "INVALIDE", "Charlie");
        System.out.println("[5] Le ticket est désormais invalide\n");

        blockchain.displayChain();
        System.out.println("Chaîne valide : " + blockchain.isChainValid());
        blockchain.exportAsJson("blockchain.json");
    }

    private static void apiConsole() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Choisir le consensus :");
        System.out.println("1. Proof of Work");
        System.out.println("2. Proof of Stake");
        System.out.println("3. PBFT");
        System.out.println("4. Proof of Authority");
        System.out.print("Choix : ");
        String consChoice = scanner.nextLine().trim();

        ConsensusAlgorithm consensus = switch (consChoice) {
            case "1" -> new ProofOfWork(2);
            case "2" -> {
                ProofOfStake pos = new ProofOfStake();
                pos.addStake("Validateur-A", 50);
                pos.addStake("Validateur-B", 30);
                yield pos;
            }
            case "3" -> {
                PBFT pbft = new PBFT();
                pbft.addNode("Noeud-1");
                pbft.addNode("Noeud-2");
                pbft.addNode("Noeud-3");
                pbft.addNode("Noeud-4");
                yield pbft;
            }
            case "4" -> {
                ProofOfAuthority poa = new ProofOfAuthority();
                poa.addAuthority("Admin-Principal");
                poa.addAuthority("Admin-Secondaire");
                yield poa;
            }
            default -> {
                System.out.println("Choix invalide, PoW par défaut.");
                yield new ProofOfWork(2);
            }
        };

        Blockchain blockchain = new Blockchain(consensus);

        while (true) {
            System.out.println("\n1. Ajouter un bloc");
            System.out.println("2. Afficher la chaîne");
            System.out.println("3. Vérifier l'intégrité");
            System.out.println("4. Exporter en JSON");
            System.out.println("5. Quitter");
            System.out.print("Choix : ");

            String choix = scanner.nextLine().trim();

            switch (choix) {
                case "1" -> {
                    System.out.print("ID évènement : ");
                    String eventId = scanner.nextLine();
                    System.out.print("Artiste : ");
                    String artist = scanner.nextLine();
                    System.out.print("Statut (ACHETE/REVENDU/UTILISE/INVALIDE) : ");
                    String status = scanner.nextLine();
                    System.out.print("Propriétaire : ");
                    String owner = scanner.nextLine();

                    String lastStatus = blockchain.getLastStatus();
                    if ("INVALIDE".equals(lastStatus) || "UTILISE".equals(lastStatus)) {
                        System.out.println("Impossible : le dernier ticket est " + lastStatus);
                    } else {
                        blockchain.addBlock(eventId, artist, status, owner);
                        System.out.println("Bloc ajouté !");
                    }
                }
                case "2" -> blockchain.displayChain();
                case "3" -> System.out.println("Chaîne valide : " + blockchain.isChainValid());
                case "4" -> blockchain.exportAsJson("blockchain.json");
                case "5" -> {
                    System.out.println("Au revoir !");
                    return;
                }
                default -> System.out.println("Choix invalide.");
            }
        }
    }
}
