# Guide de Test avec cURL - MentorMatch API

## 🚀 Démarrage Rapide

### 1. Démarrer l'application
```bash
cd MentorMatch
mvn spring-boot:run
```

## 📋 Tests avec cURL

### Étape 1 : Initialiser les données de test

#### 1.1 Créer l'administrateur
```bash
curl -X POST http://localhost:8080/api/admin/init
```

**Réponse attendue :**
```json
{
  "message": "Admin created successfully",
  "username": "admin",
  "password": "admin123"
}
```

#### 1.2 Créer des mentors de test
```bash
curl -X POST http://localhost:8080/api/test/mentors/init
```

**Réponse attendue :**
```json
{
  "message": "Mentors de test créés avec succès",
  "count": 5,
  "mentors": [...]
}
```

### Étape 2 : Tester l'authentification

#### 2.1 Connexion Admin
```bash
curl -X POST http://localhost:8080/api/admin/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "admin",
    "password": "admin123"
  }'
```

**Réponse attendue :**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "user": {
    "id": 1,
    "nom": "Admin",
    "email": "admin@example.com",
    "role": "ADMIN"
  }
}
```

**⚠️ IMPORTANT :** Copiez le token de la réponse pour les étapes suivantes !

### Étape 3 : Tester les opérations CRUD des Mentors

#### 3.1 Récupérer tous les mentors (GET - Public)
```bash
curl -X GET http://localhost:8080/api/mentors
```

#### 3.2 Récupérer un mentor par ID (GET - Public)
```bash
curl -X GET http://localhost:8080/api/mentors/1
```

#### 3.3 Créer un nouveau mentor (POST - Protégé)
```bash
curl -X POST http://localhost:8080/api/mentors \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer VOTRE_TOKEN_JWT" \
  -d '{
    "nom": "Nouveau Mentor",
    "email": "nouveau.mentor@example.com",
    "competences": "Vue.js, JavaScript, CSS",
    "experience": "2 ans d'expérience en développement frontend",
    "available": true,
    "role": "MENTOR"
  }'
```

**⚠️ REMPLACEZ `VOTRE_TOKEN_JWT` par le token obtenu à l'étape 2.1**

#### 3.4 Mettre à jour un mentor (PUT - Protégé)
```bash
curl -X PUT http://localhost:8080/api/mentors/1 \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer VOTRE_TOKEN_JWT" \
  -d '{
    "id": 1,
    "nom": "Jean Dupont Modifié",
    "email": "jean.dupont@example.com",
    "competences": "Java, Spring Boot, Microservices, Docker",
    "experience": "6 ans d'expérience en développement",
    "available": true,
    "role": "MENTOR"
  }'
```

#### 3.5 Supprimer un mentor (DELETE - Protégé)
```bash
curl -X DELETE http://localhost:8080/api/mentors/1 \
  -H "Authorization: Bearer VOTRE_TOKEN_JWT"
```

### Étape 4 : Tester les endpoints de recherche

#### 4.1 Recherche par terme
```bash
curl -X GET "http://localhost:8080/api/mentors/search?q=java"
```

#### 4.2 Recherche par compétences
```bash
curl -X GET http://localhost:8080/api/mentors/competences/Java
```

#### 4.3 Filtrage par disponibilité
```bash
curl -X GET "http://localhost:8080/api/mentors?available=true"
```

#### 4.4 Filtrage par compétences (paramètre)
```bash
curl -X GET "http://localhost:8080/api/mentors?competences=React"
```

## 🔧 Tests de Validation

### Test 1 : Créer un mentor sans authentification (devrait échouer)
```bash
curl -X POST http://localhost:8080/api/mentors \
  -H "Content-Type: application/json" \
  -d '{
    "nom": "Test Sans Auth",
    "email": "test@example.com",
    "competences": "Test",
    "experience": "Test",
    "available": true,
    "role": "MENTOR"
  }'
```

**Réponse attendue :** `401 Unauthorized`

### Test 2 : Créer un mentor avec token invalide (devrait échouer)
```bash
curl -X POST http://localhost:8080/api/mentors \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer token_invalide" \
  -d '{
    "nom": "Test Token Invalide",
    "email": "test2@example.com",
    "competences": "Test",
    "experience": "Test",
    "available": true,
    "role": "MENTOR"
  }'
```

**Réponse attendue :** `401 Unauthorized`

### Test 3 : Créer un mentor avec email existant (devrait échouer)
```bash
curl -X POST http://localhost:8080/api/mentors \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer VOTRE_TOKEN_JWT" \
  -d '{
    "nom": "Test Email Existant",
    "email": "jean.dupont@example.com",
    "competences": "Test",
    "experience": "Test",
    "available": true,
    "role": "MENTOR"
  }'
```

**Réponse attendue :** `400 Bad Request` (si validation d'email unique)

## 🎯 Script de Test Complet

Créez un fichier `test_mentors.sh` :

```bash
#!/bin/bash

echo "🚀 Test complet de l'API MentorMatch"
echo "=================================="

BASE_URL="http://localhost:8080"

# 1. Initialiser l'admin
echo "1. Initialisation de l'admin..."
ADMIN_RESPONSE=$(curl -s -X POST $BASE_URL/api/admin/init)
echo "Admin: $ADMIN_RESPONSE"

# 2. Créer des mentors de test
echo "2. Création des mentors de test..."
MENTORS_RESPONSE=$(curl -s -X POST $BASE_URL/api/test/mentors/init)
echo "Mentors: $MENTORS_RESPONSE"

# 3. Se connecter
echo "3. Connexion admin..."
LOGIN_RESPONSE=$(curl -s -X POST $BASE_URL/api/admin/login \
  -H "Content-Type: application/json" \
  -d '{"username": "admin", "password": "admin123"}')

# Extraire le token
TOKEN=$(echo $LOGIN_RESPONSE | grep -o '"token":"[^"]*"' | cut -d'"' -f4)
echo "Token: $TOKEN"

# 4. Tester GET (public)
echo "4. Test GET mentors (public)..."
curl -s -X GET $BASE_URL/api/mentors | head -c 100
echo ""

# 5. Tester POST (protégé)
echo "5. Test POST mentor (protégé)..."
curl -s -X POST $BASE_URL/api/mentors \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TOKEN" \
  -d '{
    "nom": "Test cURL",
    "email": "test.curl@example.com",
    "competences": "cURL, API Testing",
    "experience": "Expert en tests API",
    "available": true,
    "role": "MENTOR"
  }'

echo ""
echo "✅ Tests terminés !"
```

## 🐛 Dépannage

### Problème : "401 Unauthorized"
- Vérifiez que vous avez un token JWT valide
- Vérifiez que le token commence par "Bearer "
- Vérifiez que le token n'a pas expiré

### Problème : "400 Bad Request"
- Vérifiez la structure JSON de votre requête
- Vérifiez que tous les champs obligatoires sont présents
- Vérifiez que l'email n'existe pas déjà

### Problème : "404 Not Found"
- Vérifiez que l'application est démarrée
- Vérifiez l'URL de l'endpoint
- Vérifiez que l'ID du mentor existe

### Problème : "500 Internal Server Error"
- Vérifiez les logs de l'application
- Vérifiez que la base de données est accessible
- Vérifiez la structure des données envoyées

## 📊 Codes de Réponse HTTP

- **200 OK** : Requête réussie
- **201 Created** : Ressource créée avec succès
- **204 No Content** : Suppression réussie
- **400 Bad Request** : Données invalides
- **401 Unauthorized** : Authentification requise
- **403 Forbidden** : Accès refusé
- **404 Not Found** : Ressource non trouvée
- **500 Internal Server Error** : Erreur serveur
