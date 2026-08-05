package com.luiz.satelitte_tracker.service;

import com.luiz.satelitte_tracker.model.SatellitePosition;
import com.luiz.satelitte_tracker.model.TleData;
import org.orekit.bodies.GeodeticPoint;
import org.orekit.bodies.OneAxisEllipsoid;
import org.orekit.frames.Frame;
import org.orekit.frames.FramesFactory;
import org.orekit.frames.Transform;
import org.orekit.propagation.analytical.tle.TLE;
import org.orekit.propagation.analytical.tle.TLEPropagator;
import org.orekit.time.AbsoluteDate;
import org.orekit.time.TimeScalesFactory;
import org.orekit.utils.Constants;
import org.orekit.utils.IERSConventions;
import org.orekit.utils.PVCoordinates;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class OrbitService {

    public SatellitePosition calculate(TleData tle, Instant time) {

        AbsoluteDate date = new AbsoluteDate(
                time,
                TimeScalesFactory.getUTC()
        );

        TLE tleObject = new TLE(
                tle.lineOne(),
                tle.lineTwo()
        );

        TLEPropagator propagator =
                TLEPropagator.selectExtrapolator(tleObject);

        PVCoordinates pv =
                propagator.getPVCoordinates(date);


        Frame inertialFrame = FramesFactory.getEME2000();

        Frame earthFrame = FramesFactory.getITRF(
                IERSConventions.IERS_2010,
                true
        );


        Transform transform =
                inertialFrame.getTransformTo(
                        earthFrame,
                        date
                );


        PVCoordinates earthPV =
                transform.transformPVCoordinates(pv);


        OneAxisEllipsoid earth =
                new OneAxisEllipsoid(
                        Constants.WGS84_EARTH_EQUATORIAL_RADIUS,
                        Constants.WGS84_EARTH_FLATTENING,
                        earthFrame
                );


        GeodeticPoint point =
                earth.transform(
                        earthPV.getPosition(),
                        earthFrame,
                        date
                );


        double latitude =
                Math.toDegrees(point.getLatitude());

        double longitude =
                Math.toDegrees(point.getLongitude());

        double altitude =
                point.getAltitude();


        return new SatellitePosition(
                latitude,
                longitude,
                altitude
        );
    }
}