let checkid;
let checkpw;
let checkpwsame;
let checknick;

// 아이디 유효성 검사 및 중복확인
const idCheck = () => {
    let id = document.getElementById("memberId").value;
    let checkResult = document.getElementById("check-result");
    const idRegex = /^(?=.*[a-z])(?=.*[0-9])[a-z0-9]{8,16}$/;

    if (!idRegex.test(id)) {
        checkResult.style.color = "red";
        checkResult.innerHTML = "아이디는 영문 소문자와 숫자로 구성된 8~16자여야 합니다.";
        checkid = false;
        return;
    }

    $.ajax({
        type: "post",
        url: "/member/id-check",
        data: {
            "memberId": id
        },
        success: function(res) {

            console.log("요청성공", res);

            if (res == "ok") {
                console.log("사용가능한 아이디");
                checkResult.style.color = "green";
                checkResult.innerHTML = "사용가능한 아이디입니다.";
                checkid = true;

            } else {
                console.log("이미 사용중인 아이디");
                checkResult.style.color = "red";
                checkResult.innerHTML = "이미 사용중인 아이디입니다.";
                checkid = false;

            }
        },
        error: function(err) {
            console.log("에러발생", err);
        }
    });
}

//비밀번호 유효성 검사 및 중복확인
let passwordCheck = () => {
let pw = document.getElementById("memberPw").value;
let pwcheckResult = document.getElementById("pwcheck-result");
const passwordRegex = /^(?!((?:[A-Za-z]+)|(?:[~!@#$%^&*()_+=]+)|(?:[0-9]+))$)[A-Za-z\d~!@#$%^&*()_+=]{10,16}$/;

if (!passwordRegex.test(pw)) {
    pwcheckResult.style.color = "red";
    pwcheckResult.innerHTML = "비밀번호는 영문 대소문자, 숫자, 특수문자 중 2가지 이상 조합된 10~16자여야 합니다.";
    checkpw = false;
    return;
}

$.ajax({
    type: "post",
    url: "/member/pw-check",
    data: {
        "memberPw": pw
    },
    success: function(res1) {
        if (res1 == "ok") {
            console.log("사용가능한 비밀번호");
            pwcheckResult.style.color = "green";
            pwcheckResult.innerHTML = "사용가능한 비밀번호입니다.";
            checkpw = true;
        } else {
            console.log("이미 사용중인 비밀번호");
            pwcheckResult.style.color = "red";
            pwcheckResult.innerHTML = "이미 사용중인 비밀번호입니다.";
            checkpw = false;
        }
    },
    error: function(err) {
        console.log("에러발생", err);
    }
  });
}


//비밀번호 확인 검사
const isPwSame = () => {

let pwcheck01Result = document.getElementById("pwcheck01-result");
console.log(pwcheck01Result.innerText); //테스트용 로그 출력
let realPw = document.getElementById("memberPw").value;
let samePw = document.getElementById("memberPw_check").value;

if(realPw != samePw) {
    pwcheck01Result.style.color = "red";
    pwcheck01Result.innerHTML = "비밀번호가 일치하지 않습니다.";
    checkpwsame = false;


} else {
    pwcheck01Result.style.color = "green";
    pwcheck01Result.innerHTML = "비밀번호가 일치합니다.";
    checkpwsame = true;
}
}

//닉네임 글자 수 검사
const checkNicknameLength = () => {

let nickResult = document.getElementById("nick-result");
console.log(nickResult.innerText); //테스트용 로그 출력
let nickName = document.getElementById("memberNick").value;

if(nickName.length > 9) {
    nickResult.style.color = "red";
    nickResult.innerHTML = "닉네임은 9글자 이하입니다.";
    checknick =  false;
} else {
    nickResult.style.color = "green";
    nickResult.innerHTML = "사용할 수 있는 닉네임입니다.";
    checknick = true;
}

}

let submitButton = document.getElementById("joinbutton");
let isValidation;
let isNull;

submitButton.addEventListener("click",function(event){

let Id = document.getElementById("memberId");
let Pw = document.getElementById("memberPw");
let Pw_check = document.getElementById("memberPw_check");
let Name = document.getElementById("memberName");
let Phone = document.getElementById("memberPhone");
let Nick = document.getElementById("memberNick");

    //유효성, 중복확인 통과했는지 검사
if(!checkid) {
    alert('아이디를 올바르게 입력하세요');
    isValidation = false;
    Id.focus();

} else if (!checkpw) {
    alert('비밀번호를 올바르게 입력하세요');
    isValidation = false;
    Pw.focus();

} else if (!checkpwsame) {
    alert('비밀번호가 일치하지 않습니다.');
    isValidation = false;
    Pw_check.focus();

} else if (!checknick) {
    alert('닉네임을 9자 이하로 입력하세요.');
    isValidation = false;
    Nick.focus();
} else
    isValidation = true;

// null 값 검사
if(Id.value.trim() === '') {
    alert('아이디를 다시 입력하세요');
    Id.focus();
    isNull = false;

} else if (Pw.value.trim() === '') {
    alert('비밀번호를 다시 입력하세요');
    isNull = false;
    Pw.focus();

} else if(Pw_check.value.trim() === '') {
    alert('비밀번호 확인을 해주세요');
    isNull = false;
    Pw_check.focus();

} else if(Name.value.trim() === '') {
    alert('이름을 입력해주세요');
    isNull = false;
    Name.focus();

} else if(Phone.value.trim() === '') {
    alert('전화번호를 입력해주세요');
    isNull = false;
    Phone.focus();

} else if(Nick.value.trim() === '') {
    alert('닉네임을 입력해주세요');
    isNull = false;
    Nick.focus();
} else
    isNull = true;

//최종점검
if(isValidation && isNull) {
    return;

} else
    event.preventDefault();

});