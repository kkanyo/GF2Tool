package com.kkanyo.gf2tool.domain.doll.repository;

import static com.kkanyo.gf2tool.domain.doll.entity.QDoll.doll;

import java.util.List;

import com.kkanyo.gf2tool.domain.doll.dto.QDollResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.kkanyo.gf2tool.domain.doll.dto.DollResponseDto;
import com.kkanyo.gf2tool.domain.doll.dto.DollSearchCondition;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DollRepositoryImpl implements DollRepositoryCustom {

    private JPAQueryFactory queryFactory;

    @Override
    public Page<DollResponseDto> search(DollSearchCondition condition, Pageable pageable) {
        List<DollResponseDto> content = queryFactory
                .select(new QDollResponseDto(
                        doll.name,
                        doll.attribute2,
                        doll.rare,
                        doll.weaponType,
                        doll.job))
                .from(doll)
                .where(
                        nameContains(condition.getName()),
                        attribute2Eq(condition.getAttribute2()),
                        rareEq(condition.getRare()),
                        weaponTypeEq(condition.getWeaponType()),
                        jobEq(condition.getJob()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .select(doll.count())
                .where(
                        nameContains(condition.getName()),
                        attribute2Eq(condition.getAttribute2()),
                        rareEq(condition.getRare()),
                        weaponTypeEq(condition.getWeaponType()),
                        jobEq(condition.getJob()))
                .from(doll)
                .fetchOne();

        return content.isEmpty() ? Page.empty(pageable) : new PageImpl<>(content, pageable, total);
    }

    public BooleanExpression nameContains(String name) {
        return name != null ? doll.name.containsIgnoreCase(name) : null;
    }

    public BooleanExpression attribute2Eq(Integer attribute2) {
        return attribute2 != null ? doll.attribute2.eq(attribute2) : null;
    }

    public BooleanExpression rareEq(Integer rare) {
        return rare != null ? doll.rare.eq(rare) : null;
    }

    public BooleanExpression weaponTypeEq(Integer weaponType) {
        return weaponType != null ? doll.weaponType.eq(weaponType) : null;
    }

    public BooleanExpression jobEq(Integer job) {
        return job != null ? doll.job.eq(job) : null;
    }
}
