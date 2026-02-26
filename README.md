# Blockchain Ticketing System

Systeme de tracabilite de tickets d'evenements base sur une blockchain avec implementation de 4 mecanismes de consensus.

**Projet TP5 -- Programmation Avancee -- Universite Paris 8 -- IUT de Montreuil**

## Structure du projet

```
src/main/java/.../jeuQuizz/
├── App.java                  # Point d'entree, demos et API console
├── Block.java                # Structure d'un bloc (SHA-256, nonce)
├── Blockchain.java           # Gestion de la chaine, validation, export JSON
├── ConsensusAlgorithm.java   # Interface commune des mecanismes de consensus
├── ProofOfWork.java          # Proof of Work (minage)
├── ProofOfStake.java         # Proof of Stake (selection ponderee)
├── PBFT.java                 # Practical Byzantine Fault Tolerance
└── ProofOfAuthority.java     # Proof of Authority (rotation d'autorites)
```

## Prerequis

- Java 17+
- Maven 3.9+

## Compilation et execution

```bash
mvn compile exec:java -Dexec.mainClass="org.univ_paris8.iut.montreuil.qdev.tp2025.gr22.jeuQuizz.App"
```

## Fonctionnalites

### Bloc (`Block.java`)

Chaque bloc contient :
- **index** : position dans la chaine
- **timestamp** : horodatage ISO 8601
- **eventId** : identifiant de l'evenement
- **artist** : artiste
- **status** : statut du ticket (`ACHETE`, `REVENDU`, `UTILISE`, `INVALIDE`)
- **owner** : proprietaire du ticket
- **nonce** : compteur pour le Proof of Work
- **hash / previousHash** : chainage cryptographique SHA-256

### Blockchain (`Blockchain.java`)

- Creation automatique du Genesis Block
- Ajout de blocs avec validation par le consensus choisi
- Verification d'integrite (`isChainValid()`) : controle des hash et du chainage
- Export en JSON (`exportAsJson()`) via Gson

### Mecanismes de consensus

| Algorithme | Principe | Classe |
|---|---|---|
| **Proof of Work** | Minage par incrementation du nonce jusqu'a obtenir un hash commencant par N zeros | `ProofOfWork` |
| **Proof of Stake** | Selection aleatoire ponderee par le stake de chaque validateur | `ProofOfStake` |
| **PBFT** | Vote en 3 phases (Pre-prepare, Prepare, Commit) tolerant (n-1)/3 noeuds byzantins | `PBFT` |
| **Proof of Authority** | Validation par rotation (round-robin) entre autorites de confiance | `ProofOfAuthority` |

### Simulation du workflow ticket

Le programme simule le cycle de vie complet d'un ticket de concert :

```
Alice achete le ticket       -> ACHETE
Alice revend a Bob           -> REVENDU
Bob revend a Charlie         -> REVENDU
Charlie entre au concert     -> UTILISE
Le ticket devient invalide   -> INVALIDE
```

Chaque etape est enregistree comme un bloc dans la blockchain, formant une trace infalsifiable.

### API Console

Menu interactif permettant de :
1. Ajouter un bloc (avec choix du consensus au demarrage)
2. Afficher la chaine complete
3. Verifier l'integrite de la blockchain
4. Exporter en JSON (`blockchain.json`)

## Dependances

- [Gson 2.10.1](https://github.com/google/gson) -- serialisation JSON
