// fileInput 찾기
const fileInput = document.querySelector('#fileInput');

// 업로드 파일 보여주기 찾기
const uploadResult = document.querySelector('.uploadResult ul');

function checkExtension(fileName) {
  // 정규식 사용
  const regex = /(.*?).(png|gif|jpg)$/;

  // txt => false, 이미지 => true
  console.log(regex.test(fileName));

  return regex.test(fileName);
}

function showUploadImages(arr) {
  console.log('showUploadImages', arr);

  let tags = '';

  // 태그 붙여넣기
  arr.forEach((obj, idx) => {
    tags += `<li data-name="${obj.fileName}" data-path="${obj.folderPath}" data-uuid="${obj.uuid}">`;
    tags += `<div>`;
    tags += `<a href=""><img src="/upload/display?fileName=${obj.thumbImageURL}" class="block"></a>`;
    tags += `<span class="text-sm d-inline-block mx-1">${obj.fileName}</span>`;
    tags += `<a href="#" data-file="${obj.imageURL}">`;
    tags += `<i class="fa-solid fa-xmark"></i></a>`;
    tags += `</div></li>`;
  });
  uploadResult.insertAdjacentHTML('beforeend', tags);
}

// fileInput change 이벤트 걸기
// checkExtension() 호출
// 이미지 파일이라면 FormData() 객체 생성 후
// append
fileInput.addEventListener('change', (e) => {
  const files = e.target.files; // 파일 입력 필드에서 선택한 파일 목록을 가져온다.

  // 스크립트에서 폼 태그 작성
  const formData = new FormData();

  // 파일 확장자를 확인하고 유효한 파일만 FormData에 추가합니다.
  for (let index = 0; index < files.length; index++) {
    if (checkExtension(files[index].name)) {
      // 파일 확장자를 확인
      formData.append('uploadFiles', files[index]); // 유효한 파일을 FormData에 추가
    }
  }

  // FormData에 추가된 파일을 콘솔에 출력
  for (const value of formData.values()) {
    console.log(value);
  }

  // 파일을 서버로 업로드하기 위해 fetch를 사용하여 POST 요청을 보냄
  fetch('/upload/uploadAjax', {
    method: 'post', // HTTP 메서드를 POST로 설정
    body: formData, // FormData 객체를 요청의 본문으로 설정
  })
    .then((response) => response.json()) // 서버 응답을 JSON 형식으로 변환
    .then((data) => {
      console.log(data);

      // 업로드된 이미지를 화면에 표시하는 함수를 호출
      showUploadImages(data);
    });
});

// register, modify 중복 사용
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
