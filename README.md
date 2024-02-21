<div align="center">

  <h1> <i>' COM-MA '</i> <br>AI Camera-Based Sign Language Education Platform for CODA</h1><br>

  <img src="https://github.com/COM-MA/COM-MA-SERVER/assets/102026726/5cdd7168-3cee-4385-8944-c41d85ae18e4" alt="Slide 16_9 - 4">
</div>


## 📍Table of Contents

-  [💟 Backend Team Members](#-backend-member)
-  [💟 Background](#-backgound)
-  [💟 Project Overview](#-project-overview)
-  [💟 UN SDG GOALS](#-un-sdg-goals)
-  [💟 Key Features](#-key-features)
    - [🔎 COM-MA Lens](#-com-ma-lens)
    - [🏷️ Word Cards](#-word-cards)
    - [💡 Quizzes](#-quizzes)
    - [📚 Storybooks](#-storybooks)
    - [🟣 Daily Mission Stickers](#-daily-mission-stickers)
-  [💟 How to Run](#-how-to-run)
-  [💟 Tech Stack](#-tech-stack)
    - [🖥 Backend Server](#-backend-server)
-  [💟 System Architecture](#-system-architecture)
    - [📂 Folder Structure](#-folder-structure)
    - [⚙️ Architecture Structure](#-architecture-structure)
    - [📋 ERD](#-erd)
-  [💟 Commit Convention](#-commit-convention)

<br>

## 💟 Backend Member

| TEAM 👨‍👦‍👦  | Name 👩‍💻  | Major 🖥         | Contact mail 📧    |
|----------|-------|------------------|--------------------|
|Green | Haeyeon Song | Computer Science | hysong4u.gmail.com |
<br>

## 💟 Background
In South Korea, a significant portion of the 400,000 registered deaf individuals are married, and the majority of their children, known as CODAs (Children of Deaf Adults), are hearing. 
<br>

> <b>Challenges faced by CODAs </b> <br>
CODAs face challenges such as identity confusion in navigating a mixed cultural and linguistic environment, and they often bear the burden of facilitating communication between the deaf and hearing communities. 
<br>

> <b> The Need for CODA to Learn Sign Language </b> <br>
Recognizing the importance of effective communication, there is a need for CODAs to learn sign language actively. Learning sign language fosters communication with their deaf parents, reducing difficulties and preventing emotional isolation. Adequate interaction with parents during early childhood is crucial for the linguistic and emotional development of CODAs.

<br>

## 💟 Project Overview

> <i> 'Connect the Silent World and the World of Sounds'</i>

`COM-MA` is an "AI camera-based sign language learning education platform" that helps deaf parents teach sign language directly to their children, <b>CODAs</b> (Children of Deaf Adults).
<br><br>The `COM-MA` Lens recognizes sign language and quickly converts it into learning materials for deaf parents. It offers these parents the opportunity to engage in sign language education with their children in everyday life.

<br />

## 💟 UN SDG GOALS 
<br />

![image](https://github.com/COM-MA/COM-MA-SERVER/assets/102026726/247c2d7b-dfc7-400f-94a4-af8a6d085354)
### ✨ 1. Quality education ([4] among Sustainable Development Goals)
We are focusing on `quality education` among the UN's sustainable development goals. This goal is to provide comprehensive and fair educational opportunities for all individuals and to expand opportunities for lifelong learning.

To respond to the educational challenges faced by Coda, we want to provide a better educational environment for Coda. Our solution supports  <b>personalized learning in consideration of linguistic and cultural diversity</b>, and <b>provides the opportunity </b>to open up a better future through continuous lifelong learning.
<br>
<br />
<br />

![image](https://github.com/COM-MA/COM-MA-SERVER/assets/102026726/31c690a1-1c82-4f52-9be7-66087140b2db)
### ✨ 2. Reduced inequalities ([10] among Sustainable Development Goals)

The goal of `Reduced inequalities ` is to reduce inequality at home and abroad and allow everyone to enjoy equal opportunities.

Our solutions contribute to <b>overcoming the linguistic and cultural gaps</b> that Koda is experiencing, and provide Coda with <b?opportunities to address social inequality</b>. Through this, we want to support Coda's future to brighten up, and help address social inequality.
<br />
<br />
## 💟 Key Features

![System Architecture](https://github.com/COM-MA/COM-MA-SERVER/assets/102026726/58d603fb-5e4c-45f9-a767-4d7a5206217c)

### 🔎 COM-MA Lens
> AI-based camera recognizes sign language and provides images, text, and voice.
<br>

### 🏷️ Word Cards
> Cards for learning sign language, text, and voice for each word.
<br>

### 💡 Quizzes
> Create quizzes directly from saved word cards for educational use.
<br>

### 📚 Storybooks
> Easily watch and educate with subtitles or sign language provided in storybooks.
<br>

### 🟣 Daily Mission Stickers
> Motivate users with one-day mission stickers such as word registration, quiz participation, and fairy tale viewing.

<br />

## 💟 How to Run

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
<br>

## 💟 Tech Stack

### 🖥 Backend Server

| Role               | Type                                                                                                                                                                                                                                                            |
|---------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Framework           | ![Spring Boot](https://img.shields.io/badge/Spring%20Boot3-6DB33F?style=for-the-badge&logo=Spring&logoColor=white)                                                                                                                                              |
| Language            | ![Java](https://img.shields.io/badge/Java17-004027?style=for-the-badge&logo=Java&logoColor=white)                                                                                                                                                               |
| Build Tool          | ![Gradle](https://img.shields.io/badge/Gradle-02303A?style=for-the-badge&logo=Gradle&logoColor=white)                                                                                                                                                           |
| Database &  Database Service             | ![MySQL](https://img.shields.io/badge/MySQL-003545?style=for-the-badge&logo=MySQL&logoColor=white)   ![Amazon RDS](https://img.shields.io/badge/Amazon%20RDS-527FFF?style=for-the-badge&logo=AmazonRDS&logoColor=white)                                                                                                                                                                |                                                                                                                                      |
|  Cloud Server & Cloud Storage       | ![Amazon S3](https://img.shields.io/badge/Amazon%20S3-569A31?style=for-the-badge&logo=AmazonS3&logoColor=white) ![Amazon EC2](https://img.shields.io/badge/Amazon%20EC2-232F3E?style=for-the-badge&logo=AmazonEC2&logoColor=white)                                                                                                                                                |
| Google API          | ![Oauth 2.0 API](https://img.shields.io/badge/OAuth%202.0%20API-4285F4?style=for-the-badge&logo=Google&logoColor=white)  ![Youtube DATA API V3](https://img.shields.io/badge/Youtube%20DATA%20API%20V3-FF0000?style=for-the-badge&logo=YouTube&logoColor=white)          
| Web Server          | ![Nginx](https://img.shields.io/badge/Nginx-009639?style=for-the-badge&logo=Nginx&logoColor=white)                                                                                                                                                              |

<br>

## 💟 System Architecture

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
<hr>

## 💟 Commit Convention

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
