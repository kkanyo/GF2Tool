package com.kkanyo.gf2tool.domain.doll.repository;

import static com.kkanyo.gf2tool.domain.doll.entity.QDoll.doll;

import java.util.List;

import com.kkanyo.gf2tool.domain.doll.dto.QDollResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.kkanyo.gf2tool.domain.doll.dto.DollResponseDto;
import com.kkanyo.gf2tool.domain.doll.dto.DollSearchCondition;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class DollRepositoryImpl implements DollRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<DollResponseDto> search(DollSearchCondition condition, Pageable pageable) {
        List<DollResponseDto> content = queryFactory
                .select(new QDollResponseDto(
                        doll.name,
                        doll.attribute,
                        doll.rare,
                        doll.weaponType,
                        doll.job))
                .from(doll)
                .where(
                        nameContains(condition.getName()),
                        attributeEq(condition.getAttribute()),
                        rareEq(condition.getRare()),
                        weaponTypeEq(condition.getWeaponType()),
                        jobEq(condition.getJob()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory
                .select(doll.count())
                .where(
                        nameContains(condition.getName()),
                        attributeEq(condition.getAttribute()),
                        rareEq(condition.getRare()),
                        weaponTypeEq(condition.getWeaponType()),
                        jobEq(condition.getJob()))
                .from(doll)
                .fetchOne();

        long totalCount = total != null ? total : 0L;

        return content.isEmpty() ? Page.empty(pageable) : new PageImpl<>(content, pageable, totalCount);
    }

    public BooleanExpression nameContains(String name) {
        return name != null ? doll.name.containsIgnoreCase(name) : null;
    }

    public BooleanExpression attributeEq(Integer attribute) {
        return attribute != null ? doll.attribute.eq(attribute) : null;
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
