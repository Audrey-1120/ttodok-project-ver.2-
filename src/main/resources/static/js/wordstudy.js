const queryString = window.location.search;
const urlParams = new URLSearchParams(queryString);
const card = urlParams.get('card');

document.getElementById("wordStudyButton").addEventListener("click", function(event) {
    const card1 = card;
    redirectToTextLearningPage(card1);
});

$(document).ready(function(){
    $.ajax({
        type: 'GET',
        url: '/study/wordstudy?card=' + card,
        success: function(data) {

            count = data.count;
            var textTitle = data.textTitle;
            $('.wordstudy-title h3').text(textTitle);

            if (count > 0) {
                data.data.forEach(function(wordEntity) {
                    var wordContent = '<div class="word-content"><p class="word-name">' + wordEntity.word + '</p><br>' +
                                      '<p class="word-mean">' + wordEntity.wordMean + '</p><br>' +
                                      '<p class="word-plusMean">' + wordEntity.wordEx + '</p></div>';
                    var itemContent = '<div class="item">' + wordContent + '</div>';
                    $('.items').append(itemContent);
                });

                var emptyItemContent = '<div class="item"></div>';

                $('.items').append(emptyItemContent);

                    const silder = document.querySelector('#slider');
                    const wrapper = document.querySelector('.wrapper');
                    const items = document.querySelector('.items');
                    const item = document.querySelectorAll('.item');

                    const next = document.querySelector('.next');
                    const prev = document.querySelector('.prev');
                    const itemCount = item.length - 2;

                    let startX = 0;
                    let moveX = 0;
                    let currentIdx = 0;
                    let positions = [];

                    function initializeData() {

                      const isActive = items.classList.contains('active');
                      if (isActive) items.classList.remove('active');
                      const width = wrapper.clientWidth;
                      const interval = item[0].clientWidth;
                      const margin = (width - interval) / 2;
                      const initX = Math.floor((interval - margin) * -1);

                      let pos = [];
                      for (let i = 0; i < itemCount; i++) {
                        pos.push(initX - interval * i);
                      }

                      positions = pos;
                      items.style.width = (itemCount + 1) * 100 + '%';
                      items.style.left = positions[currentIdx] + 'px';
                      silder.style.visibility = 'visible';
                    }

                    initializeData();

                    next.addEventListener('click', (e) => {
                      if (currentIdx === itemCount - 1) return;
                      const isActive = items.classList.contains('active');
                      if (!isActive) items.classList.add('active');
                      currentIdx = currentIdx + 1;
                      items.style.left = positions[currentIdx] + 'px';
                    });

                    prev.addEventListener('click', (e) => {
                      if (currentIdx === 0) return;
                      const isActive = items.classList.contains('active');
                      if (!isActive) items.classList.add('active');
                      currentIdx = currentIdx - 1;
                      items.style.left = positions[currentIdx] + 'px';
                    });

            } else {
                console.log("데이터가 없습니다.");
            }
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(textStatus, errorThrown);
        }
    });
});

function redirectToTextLearningPage() {
    const apiUrl = `/textstudy?card=${card}`;
    window.location.href = apiUrl;
}