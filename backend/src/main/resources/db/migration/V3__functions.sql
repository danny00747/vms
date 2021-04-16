create or replace function get_ford_cars()
    returns json
    language plpgsql
as
$$
declare
    cars_array json;
begin
    select json_build_array(cars) as result into cars_array
    from (
             select a.license_plate,
                    a.made_in_year,
                    (select json_agg(mdl)
                     from (
                              select m.brand,
                                     m.model_type,
                                     (select json_agg(opt)
                                      from (
                                               select mo.bags_number, mo.is_automatic, mo.has_air_conditioner
                                               from cars_models_options cmo
                                                        join models_options mo on cmo.option_code = mo.option_code
                                               where cmo.model_id = m.id
                                           ) opt
                                     ) as options
                              from models m
                                       join cars c on m.id = c.model_id
                              where brand = 'Ford'
                          ) mdl
                    ) as models
             from cars as a
                      join models m2 on m2.id = a.model_id) cars;
    return cars_array;
end;
$$;
