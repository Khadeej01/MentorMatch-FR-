# Guide de Test Swagger - MentorMatch API

## 🚀 Démarrage Rapide

### 1. Démarrer l'application
```bash
cd MentorMatch
mvn spring-boot:run
```

### 2. Accéder à Swagger UI
Ouvrez votre navigateur et allez à : **http://localhost:8080/swagger-ui.html**

## 📋 Étapes de Test

### Étape 1 : Initialiser les données de test

#### 1.1 Créer l'administrateur
- **Endpoint** : `POST /api/admin/init`
- **Description** : Crée l'administrateur par défaut
- **Réponse attendue** :
```json
{
  "message": "Admin created successfully",
  "username": "admin",
  "password": "admin123"
}
```

#### 1.2 Créer des mentors de test
- **Endpoint** : `POST /api/test/mentors/init`
- **Description** : Crée 5 mentors de test avec différentes compétences
- **Réponse attendue** :
```json
{
  "message": "Mentors de test créés avec succès",
  "count": 5,
  "mentors": [...]
}
```

### Étape 2 : Tester l'authentification

#### 2.1 Connexion Admin
- **Endpoint** : `POST /api/admin/login`
- **Body** :
```json
{
  "username": "admin",
  "password": "admin123"
}
```
- **Réponse attendue** :
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

#### 2.2 Connexion Mentor (optionnel)
- **Endpoint** : `POST /api/auth/login`
- **Body** :
```json
{
  "email": "jean.dupont@example.com",
  "password": "password123"
}
```

### Étape 3 : Configurer l'authentification dans Swagger

1. Cliquez sur le bouton **"Authorize"** en haut à droite de Swagger UI
2. Dans le champ "Value", entrez : `Bearer VOTRE_TOKEN_JWT`
3. Cliquez sur **"Authorize"**
4. Cliquez sur **"Close"**

### Étape 4 : Tester les opérations CRUD des Mentors

#### 4.1 Récupérer tous les mentors (GET)
- **Endpoint** : `GET /api/mentors`
- **Description** : Récupère la liste de tous les mentors
- **Test sans authentification** : ✅ Devrait fonctionner (endpoint public)
- **Test avec authentification** : ✅ Devrait fonctionner

#### 4.2 Récupérer un mentor par ID (GET)
- **Endpoint** : `GET /api/mentors/{id}`
- **Exemple** : `GET /api/mentors/1`
- **Test sans authentification** : ✅ Devrait fonctionner (endpoint public)

#### 4.3 Créer un nouveau mentor (POST)
- **Endpoint** : `POST /api/mentors`
- **Body** :
```json
{
  "nom": "Nouveau Mentor",
  "email": "nouveau.mentor@example.com",
  "competences": "Vue.js, JavaScript, CSS",
  "experience": "2 ans d'expérience en développement frontend",
  "available": true,
  "role": "MENTOR"
}
```
- **Test sans authentification** : ❌ Devrait échouer (401 Unauthorized)
- **Test avec authentification** : ✅ Devrait fonctionner

#### 4.4 Mettre à jour un mentor (PUT)
- **Endpoint** : `PUT /api/mentors/{id}`
- **Body** :
```json
{
  "id": 1,
  "nom": "Jean Dupont Modifié",
  "email": "jean.dupont@example.com",
  "competences": "Java, Spring Boot, Microservices, Docker",
  "experience": "6 ans d'expérience en développement",
  "available": true,
  "role": "MENTOR"
}
```
- **Test sans authentification** : ❌ Devrait échouer (401 Unauthorized)
- **Test avec authentification** : ✅ Devrait fonctionner

#### 4.5 Supprimer un mentor (DELETE)
- **Endpoint** : `DELETE /api/mentors/{id}`
- **Test sans authentification** : ❌ Devrait échouer (401 Unauthorized)
- **Test avec authentification** : ✅ Devrait fonctionner

### Étape 5 : Tester les endpoints de recherche

#### 5.1 Recherche par terme
- **Endpoint** : `GET /api/mentors/search?q=java`
- **Description** : Recherche des mentors contenant "java"

#### 5.2 Recherche par compétences
- **Endpoint** : `GET /api/mentors/competences/Java`
- **Description** : Recherche des mentors avec la compétence "Java"

#### 5.3 Filtrage par disponibilité
- **Endpoint** : `GET /api/mentors?available=true`
- **Description** : Récupère seulement les mentors disponibles

#### 5.4 Filtrage par compétences (paramètre)
- **Endpoint** : `GET /api/mentors?competences=React`
- **Description** : Récupère les mentors avec la compétence "React"

## 🔧 Tests de Validation

### Tests d'erreur à effectuer :

1. **Créer un mentor avec email existant** → Devrait échouer
2. **Créer un mentor sans champs obligatoires** → Devrait échouer
3. **Récupérer un mentor inexistant** → Devrait retourner 404
4. **Mettre à jour un mentor inexistant** → Devrait échouer
5. **Supprimer un mentor inexistant** → Devrait retourner 404

### Tests d'authentification :

1. **Opérations CRUD sans token** → Devrait retourner 401
2. **Opérations CRUD avec token invalide** → Devrait retourner 401
3. **Opérations CRUD avec token valide** → Devrait fonctionner

## 📊 Endpoints de Test Utiles

- `GET /api/test/ping` - Vérifier que l'API fonctionne
- `GET /api/test/swagger` - Informations sur Swagger
- `GET /api/test/admin` - Informations sur l'admin
- `GET /api/test/mentors` - Lister tous les mentors (pour debug)

## 🎯 Résultats Attendus

Après avoir suivi ce guide, vous devriez avoir :
- ✅ 5 mentors de test créés
- ✅ Un token JWT valide
- ✅ Testé toutes les opérations CRUD
- ✅ Testé tous les endpoints de recherche
- ✅ Vérifié la sécurité des endpoints protégés

## 🐛 Dépannage

### Problème : "401 Unauthorized"
- Vérifiez que vous avez configuré l'authentification dans Swagger
- Vérifiez que le token JWT est valide
- Vérifiez que le token commence par "Bearer "

### Problème : "404 Not Found"
- Vérifiez que l'application est démarrée
- Vérifiez l'URL de l'endpoint
- Vérifiez que l'ID du mentor existe

### Problème : "500 Internal Server Error"
- Vérifiez les logs de l'application
- Vérifiez que la base de données est accessible
- Vérifiez la structure des données envoyées
