// 페이지 로드시 무조건 실행
// 즉시실행함수
// fetch() - 함수 작성후 호출

// 함수 작성
// 1. function method1(){}
// 2. const(or let) method1 = ()=>{}

// 함수 실행 => 호출
// method1();

// 호이스팅(선언 안하고 먼저 호출 후 선언)
// 1 번은 호이스팅 되고, 2번은 안됨

// var 로 선언된 변수는 호이스팅 됨
// const,let 은 호이스팅 안됨

// 날짜 포맷 변경 함수
const formatDate = (data) => {
  const date = new Date(data);

  return date.getFullYear() + '/' + (date.getMonth() + 1) + '/' + date.getDate() + ' ' + date.getHours() + ':' + date.getMinutes();
};

// 댓글 목록 가져오기
// 댓글목록 보여줄 영역 가져오기
const replyList = document.querySelector('.replyList');
const replyLoaded = () => {
  fetch(`/replies/board/${bno}`)
    .then((response) => response.json())
    .then((data) => {
      console.log(data);

      // 댓글 총 수 확인
      document.querySelector('.d-inline-block').innerHTML = data.length;

      let result = '';
      data.forEach((reply) => {
        result += `<div class="d-flex justify-content-between my-2 border-bottom reply-row" data-rno="${reply.rno}">`;
        result += `<div class="p-3"><img src="/img/default.png" alt="" class="rounded-circle mx-auto d-block" style="width: 60px; height: 60px" /></div>`;
        result += `<div class="flex-grow-1 align-self-center"><div>${reply.writerName}</div>`;
        result += `<div><span class="fs-5">${reply.text}</span></div>`;
        result += `<div class="text-muted"><span class="small">${formatDate(reply.createdDate)}</span></div></div>`;
        result += `<div class="d-flex flex-column align-self-center">`;
        // 로그인 user(email) == 작성자(${reply.writerEmail})
        if (`${email}` == `${reply.writerEmail}`) {
          result += `<div class="mb-2"><button class="btn btn-outline-danger btn-sm">삭제</button></div>`;
          result += `<div><button class="btn btn-outline-success btn-sm">수정</button></div>`;
        }
        result += `</div></div>`;
      });
      // 영역에 result 보여주기
      replyList.innerHTML = result;
    });
};

replyLoaded();

// 새 댓글 등록
// 새 댓글 등록 폼 submit 시
// submit 기능 중지 / 작성자 / 댓글 가져오기 => 스크립트 객체로 변경
const replyForm = document.querySelector('#replyForm');

if (replyForm) {
  replyForm.addEventListener('submit', (e) => {
    e.preventDefault();

    const writerEmail = replyForm.querySelector('#writerEmail');
    const text = replyForm.querySelector('#text');
    // 수정인 경우에 값이 들어옴
    const rno = replyForm.querySelector('#rno');

    const reply = {
      writerEmail: writerEmail.value,
      text: text.value,
      bno: bno,
      rno: rno.value,
    };

    if (!rno.value) {
      // 새 댓글 등록
      fetch(`/replies/new`, {
        method: 'post',
        headers: {
          'content-type': 'application/json',
          'X-CSRF-TOKEN': csrfValue,
        },
        body: JSON.stringify(reply),
      })
        .then((response) => response.text())
        .then((data) => {
          if (data) {
            alert(data + ' 번 댓글 등록');

            // replyForm 내용 제거
            text.value = '';

            replyLoaded();
          }
        });
    } else {
      // 댓글 수정
      fetch(`/replies/${rno.value}`, {
        method: 'put',
        headers: {
          'content-type': 'application/json',
          'X-CSRF-TOKEN': csrfValue,
        },
        body: JSON.stringify(reply),
      })
        .then((response) => response.text())
        .then((data) => {
          if (data) {
            alert(data + ' 번 댓글 수정');

            // replyForm 내용 제거
            text.value = '';
            rno.value = '';

            replyLoaded();
          }
        });
    }
  });
}

// 이벤트 전파 : 자식요소에 일어난 이벤트는 상위 요소로 전달 됨
// 댓글 삭제/수정 버튼 클릭 시 이벤트 전파로 찾아오기
// rno 가져오기
replyList.addEventListener('click', (e) => {
  // 실제 이벤트가 일어난 대상은 누구인가? e.target
  const btn = e.target;

  //  closest("요소") : 제일 가까운 상위요소 찾기
  const rno = btn.closest('.reply-row').dataset.rno;
  console.log('rno', rno);

  // 삭제 or 수정 버튼이 눌러졌을 때만 동작
  // 삭제 or 수정 버튼이 클릭이 되었는지 구분하기
  if (btn.classList.contains('btn-outline-danger')) {
    fetch(`/replies/${rno}`, {
      method: 'delete',
      headers: {
        'X-CSRF-TOKEN': csrfValue,
      },
    })
      .then((response) => response.text())
      .then((data) => {
        if (data == 'success') {
          alert('댓글 삭제 성공');
          replyLoaded();
        }
      });
  } else if (btn.classList.contains('btn-outline-success')) {
    //rno 에 해당하는 댓글 가져온 후 댓글 등록 폼에 가져온 내용 보여주기
    fetch(`/replies/${rno}`, {
      method: 'get',
    })
      .then((response) => response.json())
      .then((data) => {
        console.log('데이터 가져오기');
        console.log(data);

        replyForm.querySelector('#rno').value = data.rno;
        replyForm.querySelector('#writerEmail').value = data.writerEmail;
        replyForm.querySelector('#writerName').value = data.writerName;
        replyForm.querySelector('#text').value = data.text;
      });
  }
});
