package com.example.board.repository.search;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.example.board.entity.Board;
import com.example.board.entity.QBoard;
import com.example.board.entity.QMember;
import com.example.board.entity.QReply;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class SearchBoardRepositoryImpl extends QuerydslRepositorySupport implements SearchBoardRepository {

    public SearchBoardRepositoryImpl() {
        super(Board.class);
    }

    @Override
    public Page<Object[]> list(String type, String keyword, Pageable pageable) {
        log.info("Board + Reply + Member join");

        // Q 클래스 사용
        QBoard board = QBoard.board;
        QMember member = QMember.member;
        QReply reply = QReply.reply;

        // @Query("select b, m from Board b left join b.writer m")

        // board 를 기준
        JPQLQuery<Board> query = from(board);
        // board 의 writer 와 member 를 join
        query.leftJoin(board.writer, member);

        // subquery >= JPAExpressions
        // 댓글 개수
        JPQLQuery<Long> replyCount = JPAExpressions.select(reply.rno.count().as("replycnt"))
                .from(reply)
                .where(reply.board.eq(board))
                .groupBy(reply.board);

        JPQLQuery<Tuple> tuple = query.select(board, member, replyCount);

        // 페이지 나누기
        Sort sort = pageable.getSort();
        sort.stream().forEach(order -> {
            Order direction = order.isAscending() ? Order.ASC : Order.DESC;
            String prop = order.getProperty();

            PathBuilder<Board> orderByExpression = new PathBuilder<>(Board.class, "board");
            tuple.orderBy(new OrderSpecifier(direction, orderByExpression.get(prop)));

            // 검색
            BooleanBuilder builder = new BooleanBuilder();
            // board 의 bno 가 0 보다 커야함
            builder.and(board.bno.gt(0L));

            // where gno > 0 and title like '%Title%' or contetn like '%content%'
            // gno > 0

            // 검색 타입이 있는 경우
            BooleanBuilder conditionBuilder = new BooleanBuilder();
            if (type.contains("t")) {
                conditionBuilder.or(board.title.contains(keyword));
            }

            if (type.contains("c")) {
                conditionBuilder.or(board.content.contains(keyword));
            }

            if (type.contains("w")) {
                conditionBuilder.or(member.email.contains(keyword));
            }
            builder.and(conditionBuilder);

            tuple.where(builder);
        });

        // 실제 페이지 처리하는 구간
        tuple.offset(pageable.getOffset());
        tuple.limit(pageable.getPageSize());

        List<Tuple> result = tuple.fetch();
        // 전체 개수
        long count = tuple.fetchCount();

        List<Object[]> list = result.stream().map(t -> t.toArray()).collect(Collectors.toList());

        return new PageImpl<>(list, pageable, count);

    }

    @Override
    public Object[] getRow(Long bno) {

        // Q 클래스 사용
        QBoard board = QBoard.board;
        QMember member = QMember.member;
        QReply reply = QReply.reply;

        // @Query("select b, m from Board b left join b.writer m")

        // board 를 기준
        JPQLQuery<Board> query = from(board);
        // board 의 writer 와 member 를 join
        query.leftJoin(board.writer, member);
        // bno와 넘어온 bno 가 똑같은 거
        query.where(board.bno.eq(bno));

        // subquery >= JPAExpressions
        // 댓글 개수
        JPQLQuery<Long> replyCount = JPAExpressions.select(reply.rno.count().as("replycnt"))
                .from(reply)
                .where(reply.board.eq(board))
                .groupBy(reply.board);

        JPQLQuery<Tuple> tuple = query.select(board, member, replyCount);

        Tuple result = tuple.fetch().get(0);
        return result.toArray();
    }

}
