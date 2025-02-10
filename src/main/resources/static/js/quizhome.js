const queryString = window.location.search;
const urlParams = new URLSearchParams(queryString);
const card = urlParams.get('card');

document.getElementById("goQuiz1").addEventListener("click", function(event) {
    const card1 = card;
    redirectToTextLearningPage(card1);
});

function redirectToTextLearningPage() {
    const apiUrl = `/quiz1?card=${card}`;
    window.location.href = apiUrl;
}