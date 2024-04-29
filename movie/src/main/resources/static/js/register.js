// form submit 기능 중지
// uploadResult ul li 태그 요소 가져오기
document.querySelector('#register-form').addEventListener('submit', (e) => {
  e.preventDefault();

  const form = e.target;

  // 첨부파일 정보 수집
  const attachInfos = document.querySelectorAll('.uploadResult ul li');
  console.log(attachInfos);

  // 수집된 정보를 폼 태그 자식으로 붙여넣기
  let result = '';

  attachInfos.forEach((obj, idx) => {
    // hidden 입력 필드 3개를 생성
    // 각 hidden 필드는 movieImageDtos 배열에 있는 객체 하나의 정보를 나타낸다.
    // Spring 은 객체 배열을 자동으로 변환하지만, name 속성을 올바르게 맞춰줘야한다.

    // 파일 경로를 나타내는 hidden 필드를 생성
    result += `<input type='hidden' value='${obj.dataset.path}' name='movieImageDtos[${idx}].path'>`;
    // 파일 uuid를 나타내는 hidden 필드를 생성
    result += `<input type='hidden' value='${obj.dataset.uuid}' name='movieImageDtos[${idx}].uuid'>`;
    // 파일 imgName을 나타내는 hidden 필드를 생성
    result += `<input type='hidden' value='${obj.dataset.name}' name='movieImageDtos[${idx}].imgName'>`;
  });

  form.insertAdjacentHTML('beforeend', result);

  console.log(form.innerHTML);

  form.submit();
});

// x를 누르면 파일 삭제 요청
// a 태그 기능 중지 => href 값 가져와서 화면 출력
// 만들어진 태그는 이벤트 불가능하여 output 갖고오기
document.querySelector('.uploadResult').addEventListener('click', (e) => {
  e.preventDefault();
  console.log('x 클릭', e.target); // <i class="fa-solid fa-x"></i>

  console.log('x 클릭', e.currentTarget);

  const currentLi = e.target.closest('li');

  // data-file 에 있는 값 가져오기
  const filePath = e.target.closest('a').dataset.file; // data-file 에 있는 값을 가져올 땐 무조건 dataset."파일명"
  console.log('filePath', filePath);

  const formData = new FormData();
  formData.append('filePath', filePath);

  fetch('/upload/remove', { method: 'post', body: formData })
    .then((response) => response.json())
    .then((data) => {
      console.log(data);

      if (data) {
        // 해당 div 제거
        currentLi.remove();
      }
    });
});
