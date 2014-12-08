package be.vmm.eenvplus.sdi.model.constraint;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ METHOD, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = RefersNodeValidator.class)
@Documented
public @interface RefersNode {

	String message() default "{be.vmm.constraints.refersNode}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	NodePosition nodePosition() default NodePosition.self;

	Class<?> nodeType();

	String nodeGeometryName();

	double maxDistance() default 0.0;
}
