const form2 = document.querySelector('#searchForm');
form2.addEventListener('submit', (e) => {
  e.preventDefault();

  const type = document.querySelector('#type');
  const keyword = document.querySelector('#keyword');

  console.log(type);

  if (!type.value) {
    alert('조건을 선택해주세요');
    type.focus();
    return;
  } else if (!keyword.value) {
    alert('검색어를 입력해 주세요');
    keyword.focus();
    return;
  }

  form2.submit();
});
