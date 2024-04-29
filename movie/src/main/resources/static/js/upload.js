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
  fetch('/upload/uploadAjax', {
    method: 'post',
    body: formData,
  })
    .then((response) => response.json())
    .then((data) => {
      console.log(data);

      showUploadImages(data);
    });
});
