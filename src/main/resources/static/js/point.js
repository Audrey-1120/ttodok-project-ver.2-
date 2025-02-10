var selectProductPoint = 0;

document.getElementById("buybtn1").addEventListener("click", function(event) {
    showAlertBuy();
});

document.getElementById("buybtn2").addEventListener("click", function(event) {
    showAlertBuy();
});

document.getElementById("buybtn3").addEventListener("click", function(event) {
    showAlertBuy();
});

document.getElementById("buybtn4").addEventListener("click", function(event) {
    showAlertBuy();
});


document.querySelectorAll('.gifticon_list > div').forEach(function(gifticonElement, index) {
    gifticonElement.addEventListener('click', function() {
        var gifticonPriceElement = gifticonElement.querySelector('.gifticon_price');
        var selectProductPointValue = gifticonPriceElement.innerText;
        selectProductPoint = parseInt(selectProductPointValue, 10);

        console.log('선택한 요소의 productPoint:', selectProductPoint);
    });
});



function showAlertBuy() {

    Swal.fire({
      title: "해당 상품을 구매하시겠어요?",
      text: "멋지네요!",
      icon: "question",
      showCancelButton: true,
      confirmButtonColor: "#3085d6",
      cancelButtonColor: "#d33",
      confirmButtonText: "네!"
    }).then((result) => {
      if (result.isConfirmed) {
        buyProduct();

      } else if (result.isDenied) {

      }
    });

}

function buyProduct() {

    $.ajax({
        type: 'Post',
        url: '/pointshop/buyproduct',
        success: function(response) {
            console.log('Received response:', response);

            var currentUser = response.loginId;
            var currentPoint = response.memberPoint;

            var updatedProductPoint = 0;
            if (currentPoint >= selectProductPoint) {
                updatedProductPoint = currentPoint - selectProductPoint;
                console.log("updatedProductPoint: ",updatedProductPoint);
                updateProductPointsOnServer(currentUser, updatedProductPoint);

            } else {
                console.log("포인트가 부족해요!!");
                showAlertProductFailed();
            }

        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.error('Error fetching points:', errorThrown);
        }
    });

}

function updateProductPointsOnServer(currentUser, updatedProductPoint) {
    $.ajax({
        type: 'POST',
        url: '/pointshop/productupdatepoint',
        data: { loginId: currentUser, point: updatedProductPoint },
        success: function (data) {
              showAlertProductSuccess();
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.error('Error updatingProduct points:', errorThrown);
        }
    });
}

function showAlertProductFailed() {
    Swal.fire({
      title: "이런! 포인트가 부족해요!",
      text: "공부를 더 열심히 해봐요!",
      icon: "error"
    });
}

function showAlertProductSuccess() {
    Swal.fire({
      title: "구매가 완료되었습니다!",
      text: "축하해요!",
      icon: "success"
    });
}