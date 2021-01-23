// some config
package be.rentvehicle.config;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class ResourceConfigs {
    public enum ProductDto {
        ;

        public sealed interface Id permits Response.Private, Response.Public {
            @Positive Long id();
        }

        public sealed interface Name permits Request.Create, Response.Public, Response.Private {
            @NotBlank @Size(min = 5, max = 30, message = "teacherName must be between 5 and 30.")
            String name();
        }

        public interface Price {
            @Positive Double price();
        }

        public interface Cost {
            @Positive Double cost();
        }

        public enum Request {
            ;

            public record Create(String name, Double price, Double cost) implements Name, Price, Cost {
            }
        }

        public enum Response {
            ;

            public record Public(Long id, String name, Double price) implements Id, Name, Price {
            }

            public record Private(Long id, String name, Double price, Double cost) implements Id, Name, Price {
            }
        }

        public static <DTO extends Price & Cost> Double getMarkup(DTO dto) {
            return (dto.price() - dto.cost()) / dto.cost();
        }
    }
}
