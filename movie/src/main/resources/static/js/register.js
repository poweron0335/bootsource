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

  fetch('/upload/remove', {
    method: 'post',
    headers: {
      'X-CSRF-TOKEN': csrfValue,
    },
    body: formData,
  })
    .then((response) => response.json())
    .then((data) => {
      console.log(data);

      if (data) {
        // 해당 div 제거
        currentLi.remove();
      }
    });
});
