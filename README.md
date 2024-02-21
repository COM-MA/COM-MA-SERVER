
<div align="center">
  <h1>'Connect the Silent World and the World of Sounds'<br/>AI Camera-Based Sign Language Learning Service for Deaf Children - COM-MA</h1>
  <img src="https://github.com/COM-MA/COM-MA-SERVER/assets/102026726/5cdd7168-3cee-4385-8944-c41d85ae18e4" alt="Slide 16_9 - 4">
</div>

## 📍Table of Contents
<hr>

1. [💟 Backend Team Members](#-backend-team-members)
2. [💟 Project Overview](#-project-overview)
3. [💟 Key Features](#-key-features)
    - [🔎 COM-MA Lens](#-com-ma-lens)
    - [🏷️ Word Cards](#-word-cards)
    - [💡 Quizzes](#-quizzes)
    - [📚 Storybooks](#-storybooks)
4. [💟 How to Run](#-how-to-run)
5. [💟 Tech Stack](#-tech-stack)
    - [🖥 Backend Server](#-backend-server)
6. [💟 System Architecture](#-system-architecture)
    - [📂 Folder Structure](#-folder-structure)
    - [⚙️ Architecture Structure](#-architecture-structure)
    - [📋 ERD](#-erd)
7. [💟 Commit Convention](#-commit-convention)

<br>

## 💟 Backend Team Members
<hr>

| Name 👩‍💻  | Major 🖥         | Contact mail 📧    |
|-------|------------------|--------------------|
| Haeyeon Song | Computer Science | hysong4u.gmail.com |

## 💟 Project Overview
<hr>

> 2024 GOOGLE SOLUTION CHALLENGE - TEAM GREEN

`COM-MA` is an "AI camera-based sign language learning education platform" that helps deaf parents teach sign language directly to their children, <b>CODAs</b> (Children of Deaf Adults).
<br><br>The `COM-MA` Lens recognizes sign language and quickly converts it into learning materials for deaf parents. It offers these parents the opportunity to engage in sign language education with their children in everyday life.

<br />

## 💟 Key Features
<hr>

![System Architecture](https://github.com/COM-MA/COM-MA-SERVER/assets/102026726/58d603fb-5e4c-45f9-a767-4d7a5206217c)

### 🔎 COM-MA Lens
> AI-based camera recognizes sign language and provides images, text, and voice.

### 🏷️ Word Cards
> Cards for learning sign language, text, and voice for each word.

### 💡 Quizzes
> Create quizzes directly from saved word cards for educational use.

### 📚 Storybooks
> Easily watch and educate with subtitles or sign language provided in storybooks.

<br />

## 💟 How to Run
<hr>

```bash
# 1. Clone the project
git clone https://github.com/COM-MA/COM-MA-SERVER.git

# 2. Create the configuration file
vim COM-MA-SERVER/src/main/resources/application.yml

# 3. Build (execute at the project's root)
./gradlew build -x test

# 4. Navigate to build/libs directory
cd COM-MA-SERVER/build/libs/

# 5. Run the JAR file
java -jar comma-0.0.1-SNAPSHOT.jar

```
## 💟 Tech Stack
<hr>

### 🖥 Backend Server

| Role               | Type                                                                                                                                                                                                                                                            |
|---------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Framework           | ![Spring Boot](https://img.shields.io/badge/Spring%20Boot3-6DB33F?style=for-the-badge&logo=Spring&logoColor=white)                                                                                                                                              |
| Language            | ![Java](https://img.shields.io/badge/Java17-004027?style=for-the-badge&logo=Java&logoColor=white)                                                                                                                                                               |
| Build Tool          | ![Gradle](https://img.shields.io/badge/Gradle-02303A?style=for-the-badge&logo=Gradle&logoColor=white)                                                                                                                                                           |
| Database            | ![MySQL](https://img.shields.io/badge/MySQL-003545?style=for-the-badge&logo=MySQL&logoColor=white)                                                                                                                                                              |
| Database Service    | ![Amazon RDS](https://img.shields.io/badge/Amazon%20RDS-527FFF?style=for-the-badge&logo=AmazonRDS&logoColor=white)                                                                                                                                              |
| Cloud Storage       | ![Amazon S3](https://img.shields.io/badge/Amazon%20S3-569A31?style=for-the-badge&logo=AmazonS3&logoColor=white)                                                                                                                                                 |
| Cloud Server        | ![Amazon EC2](https://img.shields.io/badge/Amazon%20EC2-232F3E?style=for-the-badge&logo=AmazonEC2&logoColor=white)                                                                                                                                              |
| Google API          | ![Oauth 2.0 API](https://img.shields.io/badge/OAuth%202.0%20API-4285F4?style=for-the-badge&logo=Google&logoColor=white)  ![Youtube DATA API V3](https://img.shields.io/badge/Youtube%20DATA%20API%20V3-FF0000?style=for-the-badge&logo=YouTube&logoColor=white) || Video API           
| Web Server          | ![Nginx](https://img.shields.io/badge/Nginx-009639?style=for-the-badge&logo=Nginx&logoColor=white)                                                                                                                                                              |

## 💟 System Architecture
<hr>

### 📂 Folder Structure
```
📂 src
┣ 📂 java.com.example.comma
┃  ┣ 📂 domain
┃  ┃  ┣ 📂 sample
┃  ┃  ┃  ┣ 📂 controller
┃  ┃  ┃  ┣ 📂 entity
┃  ┃  ┃  ┣ 📂 dto
┃  ┃  ┃  ┃  ┣ 📂 request
┃  ┃  ┃  ┃  ┣ 📂 response
┃  ┃  ┃  ┣ 📂 service
┃  ┃  ┃  ┣ 📂 domain
┃  ┃  ┃  ┣ 📂 repository
┃  ┣ 📂 global
┃  ┃  ┣ 📂 common
┃  ┃  ┣ 📂 config
┃  ┃  ┃  ┣ 📂 auth
┃  ┃  ┣ 📂 error
┃  ┃  ┃  ┣ 📂 dto
┃  ┃  ┃  ┣ 📂 exception
┃  ┃  ┃  ┣ 📂 handler
┣ 📂 resources
┃  ┣ application.yml
┣ CommaApplication.class
```
<br />
<hr>

### ⚙️ Architecture Structure
![Frame 7](https://github.com/COM-MA/COM-MA-SERVER/assets/102026726/c1a1e8aa-b4e4-4a60-a1a0-66d36d7b075a)
<br />

<hr>

### 📋 ERD
<img width="783" alt="스크린샷 2024-02-21 오후 11 38 05" src="https://github.com/COM-MA/COM-MA-SERVER/assets/102026726/b1b8dc62-79bc-4060-84ca-fd969b539b77">

<br />

## 💟 Commit Convention
<hr>

- ➕ [ADD] : Additional code or library, creation of new files excluding FEAT
- ✅ [MOD] : Code modification, internal file modification
- ✨ [FEAT] : Implementation of a new feature
- 🎗️ [UI] : UI work
- 🔀 [MERGE] : When merging different branches
- 🔨 [FIX] : Bug fixes and error resolution
- 🗑️ [DEL] : Removal of unnecessary code or files
- 📝 [DOCS] : Revision of README, WIKI, or other documentation
- ✏️ [CORRECT] : Small corrections such as grammar errors, type changes, or name changes
- 📦 [CHORE] : Changes in package structure, movement of files or code within the project
- ⏪️ [RENAME] : File renaming
- ♻️ [REFACTOR] : Major code refactoring
- 🛠 [SETTING] : Other configuration changes