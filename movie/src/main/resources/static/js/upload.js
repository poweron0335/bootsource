// fileInput 찾기
const fileInput = document.querySelector('#fileInput');

fileInput.addEventListener('change', (e) => {
  const files = e.target.files;

  // 스크립트에서 폼 태그 작성
  const formData = new FormData();

  for (let index = 0; index < files.length; index++) {
    if (checkExtension(files[index].name)) {
      formData.append('uploadFiles', files[index]);
    }
  }
  for (const value of formData.values()) {
    console.log(value);
  }
});

function checkExtension(fileName) {
  // 정규식 사용
  const regex = /(.*?).(png|gif|jpg)$/;

  // txt => false, 이미지 => true
  console.log(regex.test(fileName));

  return regex.test(fileName);
}

// fileInput change 이벤트 걸기
// checkExtension() 호출
// 이미지 파일이라면 FormData() 객체 생성 후
// append
