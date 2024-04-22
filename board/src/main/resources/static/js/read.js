// 페이지 로드시 무조건 실행
// 즉시실행함수
// fetch() - 함수 작성후 호출

// 함수 작성
// 1. function method1() {}
// 2. const(or let) method1 = () => {}

// 함수 실행 => 호출
// method1();

// 호이스팅(선언 안 하고 먼저 호출 후 선언)
// 1 번은 호이스팅 되고, 2번은 안됨

// var 로 선언된 변수는 호이스팅 됨
// const, let 은 호이스팅 안됨

// 날짜 포맷 변경 함수
const formatDate = (data) => {
  const date = new Date(data);

  return date.getFullYear() + '/' + (date.getMonth() + 1) + '/' + date.getDate() + ' ' + date.getHours() + ':' + date.getMinutes();
};

// 댓글 목록 가져오기
// 댓글 목록 보여 줄 영역 가져오기
const replyList = document.querySelector('.replyList');

// 댓글 목록을 새로고침하는 함수 정의
const replyLoaded = () => {
  console.log(replyList);

  // 서버에서 해당 게시글의 댓글 목록을 가져오는 요청 보내기
  fetch(`/replies/board/${bno}`)
    .then((response) => response.json()) // 응답 데이터를 JSON 형식으로 파싱
    .then((data) => {
      console.log(data);

      // 댓글 총 수 확인
      const replyCount = document.querySelector('.d-inline-block');
      console.log(replyCount);

      // 댓글 총 수를 가져온 데이터의 길이로 업데이트
      replyCount.innerHTML = data.length;

      let result = ''; // 댓글 목록을 보여줄 HTMl 코드를 저장할 변수 초기화
      // 댓글 데이터를 반복하여 HTML 코드 생성
      data.forEach((reply) => {
        result += `<div class="d-flex justify-content-between my-2 border-bottom reply-row" data-rno="${reply.rno}">`;
        result += `<div class="p-3"><img src="/img/default.png" alt="" class="rounded-circle mx-auto-d-block" style="width: 60px; height: 60px" /></div>`;
        result += `<div class="flex-grow-1 align-self-center"><div>${reply.replyer}</div>`;
        result += `<div><span class="fs-5">${reply.text}</span></div>`;
        result += `<div class="text-muted"><span class="small">${formatDate(reply.createdDate)}</span></div></div>`;
        result += `<div class="d-flex flex-column align-self-center"><div class="mb-2"><button class="btn btn-outline-danger btn-sm">삭제</button></div>`;
        result += `<div><button class="btn btn-outline-success btn-sm">수정</button></div>`;
        result += `</div></div>`;
      });
      // 영역에 result 보여주기
      // 댓글 목록 영역에 생성한 HTML 코드 추가
      replyList.innerHTML = result;
    });
};

replyLoaded();

// 새 댓글 등록
// 새 댓글 등록 폼 submit 시
const replyForm = document.querySelector('#replyForm');

// submit 이벤트가 발생했을 때 실행되는 함수 등록
replyForm.addEventListener('submit', (e) => {
  e.preventDefault();

  // 댓글 작성자와 내용 입력값 가져오기
  const replyer = replyForm.querySelector('#replyer');
  const text = replyForm.querySelector('#text');
  // 수정인 경우에 값이 들어옴
  const rno = replyForm.querySelector('#rno');

  // 댓글 객체 생성
  const reply = {
    bno: bno, // 게시글 번호
    replyer: replyer.value, // 작성자
    text: text.value, // 내용
    rno: rno.value,
  };

  if (!rno.value) {
    // 새 댓글 등록
    // 서버로 댓글 등록 요청 보내기
    fetch(`/replies/new`, {
      method: 'post', // POST 방식으로 요청
      headers: {
        'content-type': 'application/json', // JSON 형식으로 데이터 전송
      },
      body: JSON.stringify(reply), // 댓글 데이터를 JSON 문자열로 변환하여 전송
    })
      .then((response) => response.text()) // 응답 텍스트를 가져옴
      .then((data) => {
        // 응답 데이터가 있을 경우
        if (data) {
          alert(data + ' 번 댓글 등록');

          // replyForm 내용 제거
          // 댓글 작성자와 내용 입력칸 비우기
          replyer.value = '';
          text.value = '';

          // 댓글 목록 다시 불러오기
          replyLoaded();
        }
      });
  } else {
    // 댓글 수정
    fetch(`/replies/${rno.value}`, {
      method: 'put',
      headers: {
        'content-type': 'application/json',
      },
      body: JSON.stringify(reply),
    })
      .then((response) => response.text())
      .then((data) => {
        if (data) {
          alert('수정성공');

          replyer.value = '';
          text.value = '';
          rno.value = '';

          replyLoaded();
        }
      });
  }
});

// 이벤트 전파 : 자식요소에 일어난 이벤트는 상위 요소로 전달 됨
// 댓글 삭제 / 수정 버튼 클릭 시 이벤트 전파로 찾아오기
// rno 가져오기

// 댓글 목록에 이벤트 리스너 추가
replyList.addEventListener('click', (e) => {
  // 실제 이벤트가 일어난 대상은 누구인가? => e.target
  const btn = e.target;

  console.log(btn);

  // closest("요소") : 제일 가까운 상위요소 찾기
  // data- 의 값을 가져오는 건 dataset 이용
  const rno = btn.closest('.reply-row').dataset.rno;
  console.log('rno', rno);

  // 삭제 or 수정 버튼이 눌러졌을 때만 동작
  // 삭제 or 수정 버튼이 클릭이 되었는지 구분하기

  if (btn.classList.contains('btn-outline-danger')) {
    // 해당 댓글 삭제를 서버에 요청
    fetch(`/replies/${rno}`, {
      method: 'delete', // DELETE 메서드 사용
    })
      .then((response) => response.text()) // 응답 데이터를 텍스트로 변환
      .then((data) => {
        if (data == 'success') {
          // 성공적으로 삭제되면 알림 표시하고 댓글 목록 새로고침
          alert('댓글 삭제 성공');
          replyLoaded();
        }
      });
  } else if (btn.classList.contains('btn-outline-success')) {
    // rno 에 해당하는 댓글 가져온 후 댓글 등록 폼에 가져온 내용 보여주기

    fetch(`/replies/${rno}`, {
      method: 'get',
    })
      .then((response) => response.json())
      .then((data) => {
        console.log(data);

        replyForm.querySelector('#rno').value = data.rno;
        replyForm.querySelector('#replyer').value = data.replyer;
        replyForm.querySelector('#text').value = data.text;
      });
  }
});
