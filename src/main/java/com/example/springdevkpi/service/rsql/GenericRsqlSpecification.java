package com.example.springdevkpi.service.rsql;

import cz.jirutka.rsql.parser.ast.ComparisonOperator;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GenericRsqlSpecification<T> implements Specification<T> {

    String property;
    ComparisonOperator operator;
    List<String> arguments;

    @Override
    public Predicate toPredicate(@NonNull Root<T> root, @NonNull CriteriaQuery<?> query, @NonNull CriteriaBuilder builder) {
        List<Object> args = castArguments(root);
        Object argument = args.get(0);
        switch (Objects.requireNonNull(RsqlSearchOperation.getSimpleOperator(operator))) {

            case EQUAL: {
                if (argument instanceof String) {
                    return builder.like(root.get(property), argument.toString().replace('*', '%'));
                } else if (argument == null) {
                    return builder.isNull(root.get(property));
                } else {
                    return builder.equal(root.get(property), argument);
                }
            }
            case NOT_EQUAL: {
                if (argument instanceof String) {
                    return builder.notLike(root.get(property), argument.toString().replace('*', '%'));
                } else if (argument == null) {
                    return builder.isNotNull(root.get(property));
                } else {
                    return builder.notEqual(root.get(property), argument);
                }
            }
            case GREATER_THAN: {
                return builder.greaterThan(root.get(property), argument.toString());
            }
            case GREATER_THAN_OR_EQUAL: {
                return builder.greaterThanOrEqualTo(root.get(property), argument.toString());
            }
            case LESS_THAN: {
                return builder.lessThan(root.get(property), argument.toString());
            }
            case LESS_THAN_OR_EQUAL: {
                return builder.lessThanOrEqualTo(root.get(property), argument.toString());
            }
            case IN:
                return root.get(property).in(args);
            case NOT_IN:
                return builder.not(root.get(property).in(args));
        }
        return null;
    }

    private List<Object> castArguments(final Root<T> root) {

        Class<?> type = root.get(property).getJavaType();

        return Collections.singletonList(arguments.stream().map(arg -> {
            if (type.equals(Integer.class)) {
                return Integer.parseInt(arg);
            } else if (type.equals(Long.class)) {
                return Long.parseLong(arg);
            } else {
                return arg;
            }
        }).toList());
    }

}
