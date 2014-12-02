package be.vmm.eenvplus.sdi.model.constraint;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.vividsolutions.jts.geom.Geometry;

public class GeometryTypeValidator implements
		ConstraintValidator<GeometryType, Geometry> {

	private Set<String> types;

	@Override
	public void initialize(GeometryType annotation) {
		types = new HashSet<String>(Arrays.asList(annotation.value()));
	}

	@Override
	public boolean isValid(Geometry value, ConstraintValidatorContext context) {

		if (value == null)
			return true;
		return types.contains(value.getGeometryType());
	}
}
