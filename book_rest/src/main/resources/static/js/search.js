const form2 = document.querySelector('#searchForm');
form2.addEventListener('submit', (e) => {
  e.preventDefault();

  const type = document.querySelector('#type');
  const keyword = document.querySelector("[name = 'keyword']");

  console.log(type);

  if (!type.value) {
    alert('검색 타입을 확인해 주세요');
    type.focus();
    return;
  } else if (!keyword.value) {
    alert('검색어를 확인해 주세요');
    keyword.focus();
    return;
  }

  form2.submit();
});
