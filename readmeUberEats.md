# Projet Uber Eats Replica (Authentification Client)

## Description

Ce projet est une reproduction simplifiée du système Uber Eats, développé en utilisant Jakarta EE. L'objectif principal de l'application est de fournir une authentification sécurisée uniquement pour les clients.

## Fonctionnalités

### Clients

- **Authentification sécurisée:** Les clients peuvent s'authentifier de manière sécurisée pour accéder à leur compte.
- **Navigation des menus:** Les clients peuvent parcourir les menus des dark kitchens.
- **Passer des commandes:** Les clients peuvent passer des commandes à partir des menus disponibles.
- **Recevoir des confirmations:** Les clients reçoivent des confirmations par e-mail et des notifications de statut pour leurs commandes.
- **Vérification 2FA:** Les clients peuvent activer la vérification en deux étapes pour renforcer la sécurité de leur compte.

## Exigences Non Fonctionnelles

- Toutes les communications entre les services utilisent JMS pour assurer la fiabilité.
- Capacité à supporter un volume élevé de demandes d'authentification simultanées.
- Sécurité des transactions financières et des données personnelles assurée.

## Configuration et Installation

1. Clonez le dépôt GitHub.
   ```bash
   git clone https://github.com/votre-utilisateur/uber-eats-replica.git
