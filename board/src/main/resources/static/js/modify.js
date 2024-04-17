const form = document.querySelector('#actionForm');

document.querySelector('.btn-danger').addEventListener('click', () => {
  if (!confirm('정말로 삭제하시겠습니까?')) return;

  form.action = '/board/delete';
  form.submit();
});
