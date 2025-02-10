const queryString = window.location.search;
    const urlParams = new URLSearchParams(queryString);
    const card = urlParams.get('card');

    console.log("card", card);

    document.getElementById("textStudyButton").addEventListener("click", function(event) {
            const card1 = card;
            console.log("card1", card1);
            redirectToTextLearningPage(card1);
    });

    $(document).ready(function () {
        $.ajax({
            type: 'GET',
            url: '/study/textstudy?card=' + card,
            success: function (data) {
                console.log("data: ", data);

                for (const textEntity of data.data) {
                    var htmlContent = '<div class="text-content">' +
                                        '<p class="text-content">' + $('<div/>').text(textEntity.textContent).html() + '</p>' +
                                      '</div>';
                    $('#text-container .text-content').append(htmlContent);

                    var titleContent = '<h3 class="textstudy-title">' + $('<div/>').text(textEntity.textTitle).html() + '</h3>';
                    $('.textstudy-title').append(titleContent);
                }

            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.log(textStatus, errorThrown);
            }
        });

        var isSpeaking = false;
        var speechUtterance;

        //tts
        $('#audioButton').on('click', function () {
            var contentText = $('.text-content').text();

            if (isSpeaking) {
                stopSpeech();
            } else {
                speakText(contentText);
            }
        });

        // 음성 합성
        function speakText(text) {
            var speechSynthesis = window.speechSynthesis;
            speechUtterance = new SpeechSynthesisUtterance(text);

            // 음성 합성 시작
            speechUtterance.onstart = function () {
                isSpeaking = true;
                $('#audioButton').text('Stop'); // 버튼 텍스트 업데이트
            };

            // 음성 합성 종료
            speechUtterance.onend = function () {
                isSpeaking = false;
                $('#audioButton').text('Play'); // 버튼 텍스트 업데이트
            };

            speechSynthesis.speak(speechUtterance);
        }

        // 음성 출력 중지
        function stopSpeech() {
            window.speechSynthesis.cancel(); // 음성 출력 중지
            isSpeaking = false;
            $('#audioButton').text('Play'); // 버튼 텍스트 업데이트
        }

        // 페이지 언로드 시 음성 출력 중지
        $(window).on('unload', function () {
            stopSpeech();
        });

    });

    function redirectToTextLearningPage() {
        const apiUrl = `/quizhome?card=${card}`;
        window.location.href = apiUrl;
    }