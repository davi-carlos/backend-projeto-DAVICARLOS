# 📽️ Documentação do Recomendador de Filmes API

## 📌 Visão Geral

Esta API permite aos usuários obter **recomendações de filmes** com base em suas preferências, utilizando dados da [TMDB (The Movie Database)](https://www.themoviedb.org/).

---

## ✅ Requisitos

- Java 17+
- Maven 3.8+
- Chave de API TMDB válida

---

## ⚙️ Configuração

1. **Clone o repositório**
2. **Configure sua chave da API TMDB** no arquivo `application.properties`:

```properties
tmdb.api.key=sua_chave_aqui
tmdb.api.url=https://api.themoviedb.org/3
```

3. **Execute a aplicação:**

```bash
mvn spring-boot:run
```

---

## 🔁 Rotas da API

### 1. 🔐 Salvar Preferências do Usuário

- **Método:** `POST`  
- **Endpoint:** `/api/movies/preferences`  
- **Descrição:** Armazena as preferências do usuário para recomendações futuras

#### Corpo da Requisição:

```json
{
  "genre": "28",
  "year": 2023,
  "minRating": 7.5
}
```

#### Resposta de Sucesso:

```json
"Preferências salvas com sucesso"
```

---

### 2. 📋 Listar Todas as Preferências

- **Método:** `GET`  
- **Endpoint:** `/api/movies/preferences`  
- **Descrição:** Retorna todas as preferências armazenadas

#### Resposta de Sucesso:

```json
[
  {
    "genre": "28",
    "year": 2023,
    "minRating": 7.5
  }
]
```

---

### 3. 🎯 Obter Recomendações de Filmes

- **Método:** `POST`  
- **Endpoint:** `/api/movies/recommendations`  
- **Descrição:** Retorna filmes recomendados com base nas preferências

#### Corpo da Requisição:

```json
{
  "genre": "28",
  "year": 2023,
  "minRating": 7.5
}
```

#### Resposta de Sucesso:

```json
[
  {
    "id": 575264,
    "title": "Mission: Impossible - Dead Reckoning Part One",
    "overview": "Ethan Hunt and his IMF team must track down...",
    "release_date": "2023-07-08",
    "vote_average": 7.7,
    "poster_path": "/NNxYkU70HPurnNCSiCjYAmacwm.jpg"
  }
]
```

---

### 4. ℹ️ Informações do Projeto

- **Método:** `GET`  
- **Endpoint:** `/api/movies/sobre`  
- **Descrição:** Retorna informações sobre o projeto e desenvolvedores

#### Resposta de Sucesso:

```json
{
  "integrantes": ["Davi Carlos"],
  "nome_projeto": "Recomendador de Filmes",
  "versao": "1.0"
}
```

---

## 📦 Modelos de Dados

### 🎯 `UserPreferences`

| Campo      | Tipo     | Descrição                                  |
|------------|----------|--------------------------------------------|
| genre      | String   | ID do gênero preferido (ex: `"28"` para Ação) |
| year       | Integer  | Ano de lançamento preferido                |
| minRating  | Double   | Avaliação mínima desejada (0-10)           |

### 🎬 `Movie`

| Campo        | Tipo    | Descrição                                   |
|--------------|---------|---------------------------------------------|
| id           | int     | ID do filme na TMDB                         |
| title        | String  | Título do filme                             |
| overview     | String  | Sinopse do filme                            |
| release_date | String  | Data de lançamento (YYYY-MM-DD)             |
| vote_average | double  | Média de avaliações (0-10)                  |
| poster_path  | String  | Caminho parcial para o pôster               |

---

## 🎭 Gêneros de Filmes (IDs TMDB)

Alguns IDs de gêneros comuns:

- `28`: Ação  
- `12`: Aventura  
- `16`: Animação  
- `35`: Comédia  
- `18`: Drama  
- `27`: Terror  
- `10749`: Romance  
- `878`: Ficção Científica  

Para a lista completa de gêneros, consulte a [documentação oficial da TMDB](https://developer.themoviedb.org/docs).

---
```
