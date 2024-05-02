// /reviews/3/all 요청 처리
// 날짜 포맷 변경 함수
const formatDate = (data) => {
  const date = new Date(data);

  return date.getFullYear() + '/' + (date.getMonth() + 1) + '/' + date.getDate() + ' ' + date.getHours() + ':' + date.getMinutes();
};

// 다시 사용하기 위해 reviewsLoaded 함수로 생성
const reviewsLoaded = () => {
  fetch(`/reviews/${mno}/all`)
    .then((response) => response.json())
    .then((data) => {
      console.log(data);

      // 리뷰 총 개수 변경
      document.querySelector('.review-cnt').innerHTML = data.length;

      let result = '';
      data.forEach((review) => {
        result += `<div class="d-flex justify-content-between my-2 border-bottom py-2 review-row" data-rno=${review.mno}>`;
        result += `<div class="flex-grow-1 align-self-center"><div><span class="font-semibold">${review.text}</span></div>`;
        result += `<div class="small text-muted"><span class="d-inline-block mr-3">${review.nickname}</span> 평점 : <span class="grade">${review.grade}</span></div>`;
        result += `<div class="text-muted"><span class="small">${formatDate(review.createdDate)}</span></div>
          </div>`;
        result += `<div class="d-flex flex-column align-self-center">`;
        result += `<div class="mb-2"><button class="btn btn-outline-danger btn-sm">삭제</button></div>`;
        result += `<div><button class="btn btn-outline-success btn-sm">수정</button></div>`;
        result += `</div></div>`;
      });
      reviewList.innerHTML = result;
    });
};

reviewsLoaded();

// 리뷰 등록
// 리뷰 폼 submit 중지
// text, grade, mid

const reviewForm = document.querySelector('.review-form');

reviewForm.addEventListener('submit', (e) => {
  e.preventDefault();

  const text = reviewForm.querySelector('#text');
  const mid = document.querySelector('#mid');
  const nickname = document.querySelector('#nickname');

  console.log(text);
  console.log(mid);

  const review = {
    text: text.value,
    mid: mid.value,
    mno: mno,
    grade: grade,
  };

  console.log(review);

  fetch(`/reviews/${mno}`, {
    method: 'post',
    headers: {
      'content-type': 'application/json',
    },
    body: JSON.stringify(review),
  })
    .then((response) => response.text())
    .then((data) => {
      nickname.value = '';
      text.value = '';
      grade = 0;

      if (data) {
        alert(data + ' 번 리뷰 등록');
      }

      reviewsLoaded();
    });
});
