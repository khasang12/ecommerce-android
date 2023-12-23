<!-- Improved compatibility of back to top link: See: https://github.com/khasang12-khmt/ecommerce-android/pull/73 -->
<a name="readme-top"></a>
<!--
*** Thanks for checking out the Best-README-Template. If you have a suggestion
*** that would make this better, please fork the repo and create a pull request
*** or simply open an issue with the tag "enhancement".
*** Don't forget to give the project a star!
*** Thanks again! Now go create something AMAZING! :D
-->



<!-- PROJECT SHIELDS -->
<!--
*** I'm using markdown "reference style" links for readability.
*** Reference links are enclosed in brackets [ ] instead of parentheses ( ).
*** See the bottom of this document for the declaration of the reference variables
*** for contributors-url, forks-url, etc. This is an optional, concise syntax you may use.
*** https://www.markdownguide.org/basic-syntax/#reference-style-links
-->
<!--
[![Contributors][contributors-shield]][contributors-url]
[![Forks][forks-shield]][forks-url]
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]
[![MIT License][license-shield]][license-url]
[![LinkedIn][linkedin-shield]][linkedin-url]-->



<!-- PROJECT LOGO -->
<br />
<div align="center">
  <a href="https://github.com/khasang12-khmt/ecommerce-android">
    <img src="https://github.com/khasang12-khmt/ecommerce-android/assets/80106348/a3e59907-0c9e-4d2e-bffe-e56d4f1d8e8c" alt="Logo" width="120" height="120" style="border: 2px solid #000;">
  </a>


  <h3 align="center">SaKa Ecommerce Mobile App (Android)</h3>

  <p align="center">
    The right address for shopping everyday !!
    <br />
    <a href="https://github.com/khasang12-khmt/ecommerce-android"><strong>Explore the docs »</strong></a>
    <br />
    <br />
    <a href="https://github.com/khasang12-khmt/ecommerce-android">View Demo</a>
    ·
    <a href="https://github.com/khasang12-khmt/ecommerce-android/issues">Report Bug</a>
    ·
    <a href="https://github.com/khasang12-khmt/ecommerce-android/issues">Request Feature</a>
  </p>
</div>



<!-- TABLE OF CONTENTS -->
**Table of Contents**
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
      <ul>
        <li><a href="#deploy-with">CI/CD With</a></li>
      </ul>
    </li>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#roadmap">Roadmap</a></li>
    <li><a href="#contributing">Contributing</a></li>
    <li><a href="#license">License</a></li>
    <li><a href="#contact">Contact</a></li>
  </ol>



<!-- ABOUT THE PROJECT -->
## About The Project

<table>
  <tr>
     <td>Home Screen</td>
     <td>Item Detail Screen</td>
    <td>Cart Screen</td>
  </tr>
  <tr>
    <td><img src="https://github.com/khasang12-khmt/ecommerce-android/assets/80106348/c5bdd653-4118-4066-871e-b25115bfdb8f" width=270 height=840></td>
    <td><img src="https://github.com/khasang12-khmt/ecommerce-android/assets/80106348/1a6c7383-13c6-489b-8517-f4d3dcfc1f0f" width=270 height=840></td>
    <td><img src="https://github.com/khasang12-khmt/ecommerce-android/assets/80106348/b0ffa221-e5c7-4de7-8f64-950fa1ff99ef" width=270 height=840></td>
  </tr>
 </table>

A Native Android Mobile App that serves core e-commerce functions.
* Login/ Logout/ Register
* View Products by Category
* Add Items to Cart
* Create Order
* View/ Modify Profile



<p align="right">(<a href="#readme-top">back to top</a>)</p>



### Built With

* Architecture Pattern: Pure MVVM (Model-View-ViewModel) and DI (Hilt)
* UI: XML Design
* Serverless API: Firebase (Firestore, Firebase Storage, Firebase Auth)
* 3rd-party Image Loader: Glide
* Other: Flow, Coroutine

![68747470733a2f2f692e696d6775722e636f6d2f615148476d4b4d2e706e67](https://github.com/khasang12-khmt/ecommerce-android/assets/80106348/e72f040a-57ee-4619-acf8-8f3611045479)

### Deploy With
* Unit Testing: JUnit5 and Mockito
* Instrumentation Testing: Espresso, Hilt Test and Firebase Test Lab
* Continuous Integration: Github Actions, SonarQube and its components to check if pushed version passed all testcases. Steps done:
  ![image](https://github.com/khasang12-khmt/ecommerce-android/assets/80106348/7e54b9f2-6ac4-4712-b1ed-bc312a35603a)

* Continuous Delivery: Github Actions and its components to build a signed APK/ABB, then deploy it to Google Play Store.
  ![image](https://github.com/khasang12-khmt/ecommerce-android/assets/80106348/036b7265-0020-4b91-9aca-9021fdb9bbb1)

![1_ICOIFVTu5IlAZGGijYezkg](https://github.com/khasang12-khmt/ecommerce-android/assets/80106348/3a115972-8f57-4429-9d5b-3d2d4d10f37b)



<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- ROADMAP -->
## Roadmap

- [x] Add Unit Test
- [x] Add Instrumentation Test
- [x] Add CI
- [x] Add CD
- [ ] Add Architecture Design Explanation
- [ ] Add Demo Video 
- [ ] Add "components" document to easily copy & paste sections of the readme
- [ ] Multi-language Support
    - [ ] English
    - [ ] Vietnamese

See the [open issues](https://github.com/khasang12-khmt/ecommerce-android/issues) for a full list of proposed features (and known issues).

<p align="right">(<a href="#readme-top">back to top</a>)</p>


<!-- GETTING STARTED -->
<!--## Getting Started

This is an example of how you may give instructions on setting up your project locally.
To get a local copy up and running follow these simple example steps.

### Prerequisites

This is an example of how to list things you need to use the software and how to install them.
* npm
  ```sh
  npm install npm@latest -g
  ```-->

<!--### Installation

_Below is an example of how you can instruct your audience on installing and setting up your app. This template doesn't rely on any external dependencies or services._

1. Get a free API Key at [https://example.com](https://example.com)
2. Clone the repo
   ```sh
   git clone https://github.com/your_username_/Project-Name.git
   ```
3. Install NPM packages
   ```sh
   npm install
   ```
4. Enter your API in `config.js`
   ```js
   const API_KEY = 'ENTER YOUR API';
   ```

<p align="right">(<a href="#readme-top">back to top</a>)</p>-->



<!-- USAGE EXAMPLES -->
## Usage

Use this space to show useful examples of how a project can be used. Additional screenshots, code examples and demos work well in this space. You may also link to more resources.

_For more examples, please refer to the [Documentation](https://example.com)_

<p align="right">(<a href="#readme-top">back to top</a>)</p>







<!-- CONTRIBUTING -->
## Contributing

Contributions are what make the open source community such an amazing place to learn, inspire, and create. Any contributions you make are **greatly appreciated**.

If you have a suggestion that would make this better, please fork the repo and create a pull request. You can also simply open an issue with the tag "enhancement".
Don't forget to give the project a star! Thanks again!

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- LICENSE -->
## License

Distributed under the MIT License. See `LICENSE.txt` for more information.

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- CONTACT -->
## Contact

Sang Kha - [@your_linkedin](https://www.linkedin.com/in/khasang12/) - khasang12@gmail.com

Project Link: [https://github.com/khasang12-khmt/ecommerce-android](https://github.com/khasang12-khmt/ecommerce-android)

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[contributors-shield]: https://img.shields.io/github/contributors/khasang12-khmt/ecommerce-android.svg?style=for-the-badge
[contributors-url]: https://github.com/khasang12-khmt/ecommerce-android/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/khasang12-khmt/ecommerce-android.svg?style=for-the-badge
[forks-url]: https://github.com/khasang12-khmt/ecommerce-android/network/members
[stars-shield]: https://img.shields.io/github/stars/khasang12-khmt/ecommerce-android.svg?style=for-the-badge
[stars-url]: https://github.com/khasang12-khmt/ecommerce-android/stargazers
[issues-shield]: https://img.shields.io/github/issues/khasang12-khmt/ecommerce-android.svg?style=for-the-badge
[issues-url]: https://github.com/khasang12-khmt/ecommerce-android/issues
[license-shield]: https://img.shields.io/github/license/khasang12-khmt/ecommerce-android.svg?style=for-the-badge
[license-url]: https://github.com/khasang12-khmt/ecommerce-android/blob/master/LICENSE.txt
[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=555
[linkedin-url]: https://linkedin.com/in/othneildrew
[product-screenshot]: images/screenshot.png
[Next.js]: https://img.shields.io/badge/next.js-000000?style=for-the-badge&logo=nextdotjs&logoColor=white
[Next-url]: https://nextjs.org/
[React.js]: https://img.shields.io/badge/React-20232A?style=for-the-badge&logo=react&logoColor=61DAFB
[React-url]: https://reactjs.org/
[Vue.js]: https://img.shields.io/badge/Vue.js-35495E?style=for-the-badge&logo=vuedotjs&logoColor=4FC08D
[Vue-url]: https://vuejs.org/
[Angular.io]: https://img.shields.io/badge/Angular-DD0031?style=for-the-badge&logo=angular&logoColor=white
[Angular-url]: https://angular.io/
[Svelte.dev]: https://img.shields.io/badge/Svelte-4A4A55?style=for-the-badge&logo=svelte&logoColor=FF3E00
[Svelte-url]: https://svelte.dev/
[Laravel.com]: https://img.shields.io/badge/Laravel-FF2D20?style=for-the-badge&logo=laravel&logoColor=white
[Laravel-url]: https://laravel.com
[Bootstrap.com]: https://img.shields.io/badge/Bootstrap-563D7C?style=for-the-badge&logo=bootstrap&logoColor=white
[Bootstrap-url]: https://getbootstrap.com
[JQuery.com]: https://img.shields.io/badge/jQuery-0769AD?style=for-the-badge&logo=jquery&logoColor=white
[JQuery-url]: https://jquery.com 
