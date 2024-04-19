// 제목을 클릭 시 a 태그 기능 중지
// data-id 에 있는 값을 가져오기

document.querySelector('tbody').addEventListener('click', (e) => {
  e.preventDefault(); // 클릭 이벤트의 기본 동작인 링크 이동을 막는다.

  const target = e.target; // 클릭한 요소를 타겟으로 지정

  console.log(target.dataset.id); // 클릭한 요소의 data-id 속성 값을 확인

  // 클릭한 도서의 IO를 사용하여 서버로부터 해당 도서의 정보를 가져옴
  fetch(`http://localhost:8080/read/${target.dataset.id}`)
    .then((resonse) => resonse.json())
    .then((data) => {
      console.log(data);

      // 디자인영역 가져오기
      // 가져온 도서 정보를 폼에 채움
      document.querySelector('#category').value = data.categoryName;
      document.querySelector('#title').value = data.title;
      document.querySelector('#publisher').value = data.publisherName;
      document.querySelector('#writer').value = data.writer;
      document.querySelector('#price').value = data.price;
      document.querySelector('#salePrice').value = data.salePrice;
      document.querySelector('#book_id').value = data.id;
      // 도서 ID를 숨은 필드에 설정
    });
});

// 삭제 클릭 시 id 가져오기

const button = document.querySelector('.btn-primary');
button.addEventListener('click', (e) => {
  e.preventDefault();
  // 숨은 필드에 있는 도서 ID를 가져온다
  const id = document.querySelector('#book_id').value;

  console.log(id);

  // /delete/{id} + Delete
  // get 방식이 아니라면 method 는 필수
  // 도서를 삭제하는 요청을 서버에 보낸다.
  fetch(`/delete/${id}`, {
    method: 'delete', // DELETE 메서드를 사용하여 삭제 요청
  })
    .then((response) => response.text())
    .then((data) => {
      if (data == 'success') {
        alert('삭제 성공');
        // 도서 목록 페이지로 리디렉션
        location.href = '/book/list?page=1&type=&keyword=';
      }
    });
});

document.querySelector('.btn-secondary').addEventListener('click', (e) => {
  e.preventDefault(); // 태그가 가진 기능(a, submit, reset) 중지\

  // form 내용 가져오기 -> javascript 객체 생성

  // form 이 여러개일 때 폼 안에 요소 찾기
  // const myForm = document.querySelector("#myForm");
  // myForm 안에 들어있는 요소 찾기
  // myForm.querySelector("#book_id")

  const book_id = document.querySelector('#book_id').value;
  // 자바 스크립트 객체로 만들기
  const data = {
    id: book_id,
    price: document.querySelector('#price').value,
    salePrice: document.querySelector('#salePrice').value,
  };

  // method 지정 안하면 get 으로 전송됨
  // 도서 정보를 수정하는 요청을 서버에 보낸다.
  fetch(`/modify/${book_id}`, {
    method: 'put', // PUT 메서드를 사용하여 수정 요청
    headers: {
      'content-type': 'application/json',
    },
    body: JSON.stringify(data), // JSON.stringify() : javascript 객체 => json 타입으로 변환(수정할 도서 정보를 JSON 형식으로 변환하여 보냄)
  })
    .then((response) => response.text())
    .then((data) => {
      if (data == 'success') {
        alert('수정성공'); // 수정 성공 메세지를 표시
        // 도서 목록 페이지로 리디렉션
        location.href = '/book/list?page=1&type=&keyword=';
      }
    });
});
