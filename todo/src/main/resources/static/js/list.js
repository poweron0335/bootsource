// 체크박스 클릭시 id 가져오기

// 화면의 중복 요소에 이벤트 작성
// 전체요소 찾아오기
// const checkbox = document.querySelectorAll("[name='completed']").addEvenctListener("click", (e) => {});

// 이벤트 전파 => 부모 요소가 감지
document.querySelector('.list-group').addEventListener('click', (e) => {
  console.log('이벤트가 발생한 대상 ' + e.target);
  console.log('이벤트가 발생한 대상 value ' + e.target.value);
  console.log('이벤트가 발생한 대상 ' + e.currentTarget);

  //   location.href = "/todo/update?id=" +e.target.value; ==> Get 방식
  const form = document.querySelector('#completedForm');
  form.querySelector("[name='id']").value = e.target.value;
  form.submit();
});
