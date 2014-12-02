package be.vmm.eenvplus.sdi.model.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.vividsolutions.jts.geom.Geometry;

public class GeometrySingleValidator implements
		ConstraintValidator<GeometrySingle, Geometry> {

	@Override
	public void initialize(GeometrySingle annotation) {
	}

	@Override
	public boolean isValid(Geometry value, ConstraintValidatorContext context) {

		if (value == null)
			return true;
		return value.getNumGeometries() <= 1;
	}
}
