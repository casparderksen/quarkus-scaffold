package org.example.shared.contract.adapter.in.rest;

import io.quarkus.security.AuthenticationFailedException;
import io.quarkus.security.ForbiddenException;
import io.quarkus.security.UnauthorizedException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import jakarta.ws.rs.NotAcceptableException;
import jakarta.ws.rs.NotFoundException;
import java.util.Arrays;
import java.util.Set;
import java.util.function.Supplier;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProblemScenario {
    AUTHENTICATION_FAILED("authentication-failed", 401, AuthenticationFailedException::new),
    CONSTRAINT_VIOLATION("constraint-violation", 400, () -> new ConstraintViolationException(Set.of())),
    ENTITY_NOT_FOUND("entity-not-found", 500, () -> new EntityNotFoundException("entity not found")),
    FORBIDDEN("forbidden", 403, () -> new ForbiddenException("operation forbidden")),
    ILLEGAL_ARGUMENT("illegal-argument", 500, () -> new IllegalArgumentException("illegal argument")),
    NO_RESULT("no-result", 500, () -> new NoResultException("no result")),
    NOT_ACCEPTABLE("not-acceptable", 406, () -> new NotAcceptableException("not acceptable")),
    NOT_FOUND("not-found", 404, () -> new NotFoundException("not found")),
    RUNTIME("runtime", 500, RuntimeException::new),
    UNAUTHORIZED("unauthorized", 401, UnauthorizedException::new),
    VALIDATION("validation", 500, () -> new ValidationException("validation failed"));

    private final String path;
    private final int expectedStatus;
    private final Supplier<? extends RuntimeException> exceptionSupplier;

    public static ProblemScenario fromPath(String path) {
        return Arrays.stream(ProblemScenario.values())
                .filter(s -> s.path.equals(path))
                .findFirst()
                .orElseThrow();
    }

    public RuntimeException newException() {
        return exceptionSupplier.get();
    }
}
