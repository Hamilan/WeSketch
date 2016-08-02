package shared;

/**
 * This class represents the description of a Requirement to be sketched 
 * @author Hamilan
 */
public class Requirement {
        /**
         * Requirement id
         */
        public long id;
        /**
         * Title of the requirement
         */
        public String title;
        /**
         * Description of the requirement
         */
        public String description;
        
        public Requirement() {
        }

        public Requirement(int id, String title, String description) {
                this.id = id;
                this.title= title;
                this.description = description;
        }
}