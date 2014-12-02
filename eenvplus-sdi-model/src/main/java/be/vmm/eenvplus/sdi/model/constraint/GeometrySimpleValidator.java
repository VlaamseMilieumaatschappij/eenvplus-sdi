package be.vmm.eenvplus.sdi.model.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.vividsolutions.jts.geom.Geometry;

public class GeometrySimpleValidator implements
		ConstraintValidator<GeometrySimple, Geometry> {

	@Override
	public void initialize(GeometrySimple annotation) {
	}

	@Override
	public boolean isValid(Geometry value, ConstraintValidatorContext context) {

		if (value == null)
			return true;
		return value.isSimple();
	}
}
