package com.accypiter.warriorv0_4;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * The Body is the top-level object describing a character's physical state.
 * It contains an ArrayList of roots, which are the first body parts in a particular section of the
 *      body (shoulder, hip, neck).
 * The roots are identifiable by being the only Parts with a parent value of null.
 * An organ is a Part that is a leaf; i.e. it has an empty ArrayList<Part> children.
 *
 * Body contains a function evaluateRoot or equivalent, which traverses a root and determines
 * how functional it is.
 *
 * BodyParts contain a Weapon reference, but it is left null UNLESS the Part has a null parent, i.e.
 * it is a root.
 *
 * BodyParts also contain a root_type integer, which states the type of limb it is, and is also
 * null UNLESS it is a root.
 *
 * All body parts contain an armour reference.
 *
 */

public class Body implements Serializable {
    Species species;

    ArrayList<BodyPart> roots;

    double weight;                  //Equals species base weight, plus a fraction of strength

    //Blood
    double blood_true_max;
    double blood_max;
    double blood_current;


    //Constructor
    Body(Species species) {
        this.species = species;

        this.weight = species.baseWeight();

        //Blood
        this.blood_true_max = species.baseBlood();
        this.blood_max = species.baseBlood();
        this.blood_current = species.baseBlood();

        this.roots = new ArrayList<BodyPart>();

        //generate torso root
        BodyPart torso = new BodyPart(this.species, null, null, BodyPart.LIMB_CORE, 0);
        this.roots.add(torso);
        extendPart(torso, 2); //populate torso

        BodyPart leftArm = new BodyPart(this.species, null, "left", BodyPart.LIMB_ARM, 0);
        this.roots.add(leftArm);
        extendPart(leftArm, 5);

        BodyPart rightArm = new BodyPart(this.species, null, "right", BodyPart.LIMB_ARM, 0);
        this.roots.add(rightArm);
        extendPart(rightArm, 5);

        BodyPart leftLeg = new BodyPart(this.species, null, "left", BodyPart.LIMB_LEG, 0);
        this.roots.add(leftLeg);
        extendPart(leftLeg, 5);

        BodyPart rightLeg = new BodyPart(this.species, null, "right", BodyPart.LIMB_LEG, 0);
        this.roots.add(rightLeg);
        extendPart(rightLeg, 5);

    }

    public BodyPart extendPart(BodyPart parent){
        BodyPart child = new BodyPart(parent.species, parent, parent.designation, parent.limb_type, parent.index + 1);
        parent.child = child;

        return child;
    }

    public BodyPart extendPart(BodyPart parent, int extent){
        BodyPart now_working_on = parent;
        for (int i = 0; i < extent; i++){
            now_working_on = extendPart(now_working_on);
        }
        return parent;
    }

    interface Species {
        String getSpecies();

        int numberHeads();
        int numberArms();
        int numberLegs();

        double baseWeight();
        double baseBlood();

        double speciesSize();

        double susceptibility(int attack_type);


    }

    public static class Human implements Species, Serializable{
        public String getSpecies() { return "human"; }

        public int numberHeads() { return 1; }
        public int numberArms() { return 2; }
        public int numberLegs() { return 2; }

        public double baseWeight() { return 50; }
        public double baseBlood() { return 1.5; }

        public double speciesSize() { return 1; }

        public double susceptibility(int attack_type) { return 1; }
    }
}