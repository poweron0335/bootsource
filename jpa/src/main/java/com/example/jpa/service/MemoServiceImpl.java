package com.example.jpa.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.jpa.dto.MemoDto;
import com.example.jpa.entity.Memo;
import com.example.jpa.repository.MemoRepository;

import lombok.RequiredArgsConstructor;

// @service
// public class MemoServiceImpl {
//     @Autowired
//     private MemoRepository memoRepository;

// }

@RequiredArgsConstructor // @NonNull, final 이 붙은 멤버변수를 대상으로 생성자 생성
@Service
public class MemoServiceImpl {

    private final MemoRepository memoRepository;

    // 하나씩 꺼내서 새로운 곳에 담는 작업
    public List<MemoDto> getMemoList() {
        List<Memo> entities = memoRepository.findAll();

        List<MemoDto> list = new ArrayList<>();
        entities.forEach(entity -> list.add(entityToDto(entity)));
        return list;
    }

    public MemoDto getMemo(Long mno) {
        Memo entity = memoRepository.findById(mno).get();

        return entityToDto(entity);
    }

    public MemoDto updateMemo(MemoDto mDto) {
        // 업데이트 대상 찾기
        Memo entity = memoRepository.findById(mDto.getMno()).get();

        // 변경
        entity.setMemoText(mDto.getMemoText());

        return entityToDto(memoRepository.save(entity));
    }

    public void deleteMemo(Long mno) {
        // Memo entity = memoRepository.findById(mno).get();
        // memoRepository.delete(entity);

        memoRepository.deleteById(mno);
    }

    public void insertMemo(MemoDto mDto) {
        // dto ==> entity

        memoRepository.save(dtoToEntity(mDto));
    }

    private MemoDto entityToDto(Memo entity) {
        MemoDto mDto = MemoDto.builder()
                .mno(entity.getMno())
                .memoText(entity.getMemoText())
                .build();

        return mDto;
    }

    private Memo dtoToEntity(MemoDto mDto) {
        Memo entity = Memo.builder()
                .mno(mDto.getMno())
                .memoText(mDto.getMemoText())
                .build();

        return entity;
    }
}