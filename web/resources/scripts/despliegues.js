/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(document).ready(function() {
    
    // Traducción al español
    $(function($){
        $.datepicker.regional['es'] = {
            closeText: 'Cerrar',
            prevText: '<Ant',
            nextText: 'Sig>',
            currentText: 'Hoy',
            monthNames: ['Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio', 'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'],
            monthNamesShort: ['Ene','Feb','Mar','Abr', 'May','Jun','Jul','Ago','Sep', 'Oct','Nov','Dic'],
            dayNames: ['Domingo', 'Lunes', 'Martes', 'Miércoles', 'Jueves', 'Viernes', 'Sábado'],
            dayNamesShort: ['Dom','Lun','Mar','Mié','Juv','Vie','Sáb'],
            dayNamesMin: ['Do','Lu','Ma','Mi','Ju','Vi','Sá'],
            weekHeader: 'Sm',
            dateFormat: 'dd/mm/yy',
            firstDay: 1,
            isRTL: false,
            showMonthAfterYear: false,
            yearSuffix: '',
            altFormat: "dd/mm/yy",
            changeMonth: true,
            changeYear: true
        };
        $.datepicker.setDefaults($.datepicker.regional['es']);
    });

    $(".dislipidemia").hide();
    $(".glicemia").hide();
    $(".hematocrito").hide();
    
    
    $(".rbTipoLaboratorio").click(function(){
        var value = $(this).val();
        
        //       
        // Obtener ultimo numero de control laboratorio {value}
        //
        
        value = "." + value;
        $(".dislipidemia").hide();
        $(".glicemia").hide();
        $(".hematocrito").hide();
        $(".hipertension").hide();
        $(value).show();
        if (value == ".hematocrito") {
            $(".diagnostico").hide();
        } else {
            $(".diagnostico").show();
        }
    });
    
    $(".SE").hide();
    $(".CO").hide();
    $(".CM").hide();
    
    $(".rbTipoSeguimiento").click(function(){
        var value = $(this).val();
        
        //       
        // Obtener ultimo numero de seguimiento {value}
        //
        
        //value = "." + value;
        $(".EA").hide();
        $(".SE").hide();
        $(".CO").hide();
        $(".CM").hide();
        $("." + value).show();
    });
    
    $(".lblLogaritmo").hide();
    $(".txtLogaritmo").hide();
            
    $(".rbTipoControl").click(function(){
        if ($(".txtPorcentaje").val() == "") {
           $(".txtPorcentaje").val("");
        }   
        if ($(".txtLogaritmo").val() == "") {
            $(".txtLogaritmo").val("");
        }
        $(".txtResultado").val("");
        var value = $(this).val();
        if (value == "CV") {
            //       
            // Obtener ultimo numero de control CV
            //
            
            $(".lblPorcentaje").hide();
            $(".txtPorcentaje").hide();
            $(".lblLogaritmo").show();
            $(".txtLogaritmo").show();
        } else {
            //       
            // Obtener ultimo numero de control CD4
            //
            $(".lblPorcentaje").show();
            $(".txtPorcentaje").show();
            $(".lblLogaritmo").hide();
            $(".txtLogaritmo").hide();
        }
    });
    
    $(".txtResultado").change(function(){
        $(".txtLogaritmo").val("");
        if ($("input[name='rbTipoControl']:checked").val() == "CV"){
            $(".txtLogaritmo").val((Math.log($(".txtResultado").val()) / Math.log(10)).toFixed(2));
        }
    });
    
    $(".calendar").datepicker({
        yearRange:"-50:+0",
        maxDate: 0
    });
    
    $(".calendar").attr('autocomplete','off');
    
    $(".calHastaHoy").datepicker({
        yearRange: "-50:+0",
        maxDate: 0
    });
    
    
    $(".seleccionarPaciente").click(function(){
        var pacienteID = $(this).attr("pacienteID");
        
        $(".busquedaPacienteID").val(pacienteID);
        $(".busquedaPacienteBtn").click();
    });
    
    $(".trasladarPaciente").click(function(){
        var pacienteID = $(this).attr("pacienteID");
        
        $(".busquedaPacienteID").val(pacienteID);
        $(".trasladaPacienteBtn").click();
    });
    
    
    $(".eliminarTerapia").click(function(){
        var pacienteID = $(this).attr("pacienteID");
        var terapiaID =  $(this).attr("terapiaID");
        
        $(".eliminarTerapiaVal").val(terapiaID);
        $(".eliminarTerapiaPac").val(pacienteID);
        $(".eliminarTerapiaBtn").click();
    });
    
    $(".modificarTerapia").click(function(){
        var pacienteID = $(this).attr("pacienteID");
        var terapiaID =  $(this).attr("terapiaID");
        
        $(".modificarTerapiaVal").val(terapiaID);
        $(".modificarTerapiaPac").val(pacienteID);
        $(".modificarTerapiaBtn").click();
    });
    
    $(".eliminarControl").click(function(){
        var pacienteID = $(this).attr("pacienteID");
        var controlID =  $(this).attr("controlID");
        
        $(".eliminarControlVal").val(controlID);
        $(".eliminarControlPac").val(pacienteID);
        $(".eliminarControlBtn").click();
    });
    
    $(".modificarControl").click(function(){
        var pacienteID = $(this).attr("pacienteID");
        var controlID =  $(this).attr("controlID");
        
        $(".modificarControlVal").val(controlID);
        $(".modificarControlPac").val(pacienteID);
        $(".modificarControlBtn").click();
    });
    
    //// ------------ Laboratorios
    
    $(".eliminarLaboratorio").click(function(){
        var pacienteID = $(this).attr("pacienteID");
        var controlID =  $(this).attr("laboratorioID");
        
        $(".eliminarLaboratorioVal").val(controlID);
        $(".eliminarLaboratorioControlPac").val(pacienteID);
        $(".eliminarLaboratorioBtn").click();
    });
    
    $(".modificarLaboratorio").click(function(){
        var pacienteID = $(this).attr("pacienteID");
        var controlID =  $(this).attr("laboratorioID");
        
        $(".modificarLaboratorioVal").val(controlID);
        $(".modificarLaboratorioPac").val(pacienteID);
        $(".modificarLaboratorioBtn").click();
    });
    
    ////------------
    
    /// Seguimiento
    
    $(".modificarSeguimiento").click(function(){
        var pacienteID = $(this).attr("pacienteID");
        var controlID =  $(this).attr("seguimientoID");
        $(".modificarSeguimientoVal").val(controlID);
        $(".modificarSeguimientoPac").val(pacienteID);
        $(".modificarSeguimientoBtn").click();
    });
    
    $(".eliminarSeguimiento").click(function(){
        var pacienteID = $(this).attr("pacienteID");
        var controlID =  $(this).attr("seguimientoID");
        $(".eliminarSeguimientoVal").val(controlID);
        $(".eliminarSeguimientoPac").val(pacienteID);
        $(".eliminarSeguimientoBtn").click();
    });
    
    
    // Traslado Home
    $(".aceptarTraslado").click(function(){
        var trasladoID = $(this).attr("trasladoID");
        var observacion =  $(".observacion_" + trasladoID).val();
        var destinoID = $(this).attr("destinoID");
        var pacienteID = $(this).attr("pacienteID");
        
        $(".trasladoObs").val(observacion);
        $(".trasladoVal").val(trasladoID);
        $(".trasladoDes").val(destinoID);
        $(".trasladoPac").val(pacienteID);
        $(".aceptarTrasladoBtn").click();
    });
    
    $(".rechazarTraslado").click(function(){
        var trasladoID = $(this).attr("trasladoID");
        var observacion =  $(".observacion_" + trasladoID).val();
        var destinoID = $(this).attr("destinoID");
        var pacienteID = $(this).attr("pacienteID");
        
        $(".trasladoObs").val(observacion);
        $(".trasladoVal").val(trasladoID);
        $(".trasladoDes").val(destinoID);
        $(".trasladoPac").val(pacienteID);
        $(".rechazarTrasladoBtn").click();
    });
    
    $(".eliminarTraslado").click(function(){
        var trasladoID = $(this).attr("trasladoID");
        var pacienteID = $(this).attr("pacienteID");
        
        $(".eliminarTrasladoVal").val(trasladoID);
        $(".eliminarTrasladoPac").val(pacienteID);
        $(".eliminarTrasladoBtn").click();
    });
    
    $(".esNumerico").keyup(function() {
        if ($(this).val() != null && $(this).val() != "") {
            if (!$.isNumeric($(this).val())) {
                alert("Debe ingresar un valor numérico.");
                $(this).val("");
                $(this).focus();
                return;
            } else if ($.isNumeric($(this).val()) && $(this).val() < 0) {
                alert("Debe ingresar un valor mayor a cero.");
                $(this).val("");
                $(this).focus();
                return;
            }
        }
    });
    
    $(".esNumerico").change(function(){
        if ($(this).val() != null && $(this).val() != "") {
            if (!$.isNumeric($(this).val())) {
                alert("Debe ingresar un valor numérico.");
                $(this).focus();
            } else {
                var val = $(this).val();
                val = parseInt(val);
                val = Math.round(val);
                $(this).val(val);
            }
        }
    });
    
    $(".esNumerico").change();
    
    // Fix para el radio button de Controles 
    $('input:radio').change(function() {
        if ($("input:checked").val() == "CV" || $("input:checked").val() == "CD") {
            if ($(".txtPorcentaje").val() == "") {
                $(".txtPorcentaje").val("");
            }
            if ($(".txtLogaritmo").val() == "") {
                $(".txtLogaritmo").val("");
            }   
            var val = $("input:checked").val();
            switch(val){
                case 'CD' :
                    $(".numeroControl").val($(".ultimocd4").val());
                    $(".lblPorcentaje").show();
                    $(".txtPorcentaje").show();
                    $(".lblLogaritmo").hide();
                    $(".txtLogaritmo").hide();
                    break;
                case 'CV' :
                    $(".numeroControl").val($(".ultimocv").val());
                    $(".lblPorcentaje").hide();
                    $(".txtPorcentaje").hide();
                    $(".lblLogaritmo").show();
                    $(".txtLogaritmo").show();
                    break;
            }
        }
        
        if ($("input:checked").val() == "HI" || $("input:checked").val() == "DI" || $("input:checked").val() == "GL" || $("input:checked").val() == "HE") {
            $(".dislipidemia").hide();
            $(".glicemia").hide();
            $(".hematocrito").hide();
            $(".hipertension").hide();
            $(".diagnostico").show();
            
            var val = $("input:checked").val();
            switch(val){
                case 'HI':
                    $(".hipertension").show();
                    $(".numeroControl").val($(".ultimohiper").val());
                    break;
                case 'DI':
                    $(".dislipidemia").show();
                    $(".numeroControl").val($(".ultimodisli").val());
                    break;
                case 'GL':
                    $(".glicemia").show();
                    $(".numeroControl").val($(".ultimoglice").val());
                    break;
                case 'HE':
                    $(".numeroControl").val($(".ultimohema").val());
                    $(".diagnostico").hide();
                    $(".hematocrito").show();
                    break;
            }
            
        }
        
        if ($("input:checked").val() == "EA" || $("input:checked").val() == "SE" || $("input:checked").val() == "CO" || $("input:checked").val() == "CM") {
            $(".EA").hide();
            $(".SE").hide();
            $(".CO").hide();
            $(".CM").hide();
            var val = $("input:checked").val();
            switch(val){
                case 'EA':
                    $(".EA").show();
                    if ($(".ultimo_enfermedad_asociada").val() != "" && $.isNumeric($(".ultimo_enfermedad_asociada").val())) {
                        $(".numeroControl").val($(".ultimo_enfermedad_asociada").val());
                    }
                    break;
                case 'SE':
                    $(".SE").show();
                    if ($(".ultimo_enfermedad_asociada").val() != "" && $.isNumeric($(".ultimo_enfermedad_asociada").val())) {
                        $(".numeroControl").val($(".ultimo_serologia").val());
                    }
                    break;
                case 'CO':
                    $(".CO").show();
                    if ($(".ultimo_enfermedad_asociada").val() != "" && $.isNumeric($(".ultimo_enfermedad_asociada").val())) {
                        $(".numeroControl").val($(".ultimo_control_vhb").val());
                    }
                    break;
                case 'CM':
                    if ($(".ultimo_enfermedad_asociada").val() != "" && $.isNumeric($(".ultimo_enfermedad_asociada").val())) {
                        $(".numeroControl").val($(".ultimo_control_medico").val());
                    }
                    $(".CM").show();
                    break;
            }
        }
    });
    $("input[type=radio]").change();
    if ($("input:checked") != undefined && $("input:checked").val() == "HI") {
        $(".dislipidemia").hide();
        $(".glicemia").hide();
        $(".hematocrito").hide();
        $(".hipertension").show();
        $(".numeroControl").val($(".ultimohiper").val());
        $(".diagnostico").show();
    }
    
    if ($("input:checked") != undefined && $("input:checked").val() == "DI") {
        $(".dislipidemia").show();
        $(".numeroControl").val($(".ultimodisli").val());
        $(".glicemia").hide();
        $(".hematocrito").hide();
        $(".hipertension").hide();
        $(".diagnostico").show();
    }
    
    if ($("input:checked") != undefined && $("input:checked").val() == "GL") {
        $(".dislipidemia").hide();
        $(".hematocrito").hide();
        $(".hipertension").hide();
        $(".diagnostico").show();
        $(".glicemia").show();
        $(".numeroControl").val($(".ultimoglice").val());
    }
    
    if ($("input:checked") != undefined && $("input:checked").val() == "HE") {
        $(".dislipidemia").hide();
        $(".glicemia").hide();
        $(".hipertension").hide();
        $(".numeroControl").val($(".ultimohema").val());
        $(".diagnostico").hide();
        $(".hematocrito").show();
        
    }
    
    $(".enfermedadClasificacion").change(function(){
        var val = $.trim($('.enfermedadClasificacion option:selected').text());
        $(".clasificacionLEnfermedad").val(val.substr(val.length - 1));
    });
    
    if ($("input:checked") != undefined && $("input:checked").val() == "CV") {
        $(".lblPorcentaje").hide();
        $(".txtPorcentaje").hide();
        $(".lblLogaritmo").show();
        $(".txtLogaritmo").show();
    }
    
    $(".calcLogaritmo").change(function(){
        if ($(this).val() != "" && $.isNumeric($(this).val()) && $(this).val() != "0") {
            var val = $(this).val();
            val = (Math.log(val) / Math.log(10)).toFixed(4);
            $(".basalLogaritmo").val(val);
        } else {
            $(".basalLogaritmo").val("");
        }
    });
    
    $(".calcLogaritmo").change();
    
    
    $(".cd4Clasificacion").change(function(){
        var val = $(this).val();
        if ($.isNumeric(val) && val >= 0 && val <= 2000) {
            if (val < 200) {
                $(".clasificacionNEnfermedad").val(3);
                return;
            }

            if (val > 199 && val < 500) {
                $(".clasificacionNEnfermedad").val(2);
                return;
            }

            if (val > 499) {
                $(".clasificacionNEnfermedad").val(1);
                return;
            }
        } else {
            //alert("Debe ingresar un CD4 entre 0 y 2000.");
        }
    });
    
    $(".altura").change(function(){
        if ($(".peso").val() == "" || $(".peso").val() == 0) {
            return;
        }
        var peso = parseFloat($(".peso").val());
        var altura = parseFloat($(".altura").val());
        var imc = peso / (altura * altura);
        $(".imc").val(imc.toFixed(2));
    });
    
    $(".peso").change(function(){
        if ($(".altura").val() == "" || $(".altura").val() == 0) {
            return;
        }
        var peso = parseFloat($(".peso").val());
        var altura = parseFloat($(".altura").val());
        var imc = peso / (altura * altura);
        $(".imc").val(imc.toFixed(2));
    });
    
    
    $(".txtResultado").change(function(){
        $(".txtLogaritmo").val("");
        if ($("input:checked").val() == "CV"){
            $(".txtLogaritmo").val((Math.log($(".txtResultado").val()) / Math.log(10)).toFixed(2));
        }
    });
    
    $(".ttip").hover(function(){
        var div = $(this).attr("tid");
        $("div#"+div).show();
    });
    
    $(".ttip").hover(function(){
        var div = $(this).attr("tid");
        $("div#"+div).show();
    }, function() {
        var div = $(this).attr("tid");
        $("div#"+div).hide();
    });
    
    $(".control_cd4").click(function() {
        var id = $(this).attr("cd4");
        $(".tcd4_" + id).toggle();
    })
    
    $(".control_cv").click(function() {
        var id = $(this).attr("cv");
        $(".tcv_" + id).toggle();
    })
    
    try {
        $('.inputs').eq(0).focus();
    } catch (Exception) { }
    
    $('.inputs').keydown(function (e) {
        if (e.which === 13) {
            var index = $('.inputs').index(this) + 1;
            $('.inputs').eq(index).focus();
            if ($('.inputs').size() == index) {
                $('.inputs').eq(0).focus();
                return false;
            }
            return false;
        }
    });
    
    $(".fecha_nacimiento").change(function(){
        if ($(this).val() != "") {
            //var fecha = $(this).val().split("/");
            var new_fecha = $(this).val();
            //var new_fecha = fecha[2]+"-"+fecha[1]+"-"+fecha[0];
            var date = new Date(new_fecha + " 00:00:00");
            var diff = (new Date()).getTime() - date.getTime();
            diff = Math.floor(diff / ( 24 * 3600 * 365.25 * 1000));
            $(".paciente_edad").val(diff);
        }
    });
    
    $(".fecha_nacimiento").change();
    
    
    // Fix Drogas para TAR
    $(".droga1").change(function() {
        var val1 = $(this).val();
        var val2 = $(".droga2").val();
        var val3 = $(".droga3").val();
        var val4 = $(".droga4").val();
        var val5 = $(".droga5").val();
        var val6 = $(".droga6").val();

        $(".droga2 option").each(function(){
            if ((val1 == $(this).val() || val3 == $(this).val() || val4 == $(this).val() || val5 == $(this).val() || val6 == $(this).val()) && $(this).val() != "") {
                    $(this).attr("disabled", "disabled");
            } else {
                    $(this).removeAttr("disabled");
            }
        });

        $(".droga3 option").each(function(){
            if ((val1 == $(this).val() || val2 == $(this).val() || val4 == $(this).val() || val5 == $(this).val() || val6 == $(this).val()) && $(this).val() != "") {
                    $(this).attr("disabled", "disabled");
            } else {
                    $(this).removeAttr("disabled");
            }
        });

        $(".droga4 option").each(function(){
            if ((val1 == $(this).val() || val3 == $(this).val() || $(this).val() == val2 || val5 == $(this).val() || $(this).val() == val6) && $(this).val() != "") {
                    $(this).attr("disabled", "disabled");
            } else {
                    $(this).removeAttr("disabled");
            }
        });

        $(".droga5 option").each(function(){
            if ((val1 == $(this).val() || val3 == $(this).val() || $(this).val() == val2 || val4 == $(this).val() || $(this).val() == val6) && $(this).val() != "") {
                    $(this).attr("disabled", "disabled");
            } else {
                    $(this).removeAttr("disabled");
            }
        });

        $(".droga6 option").each(function(){
            if ((val1 == $(this).val() ||val3 == $(this).val() || $(this).val() == val2 || val4 == $(this).val() || $(this).val() == val5) && $(this).val() != "") {
                    $(this).attr("disabled", "disabled");
            } else {
                    $(this).removeAttr("disabled");
            }
        });
    });
    $(".droga1").change();
    
    $(".droga2").change(function() {
        var val1 = $(".droga1").val();
            var val2 = $(this).val();
            var val3 = $(".droga3").val();
            var val4 = $(".droga4").val();
            var val5 = $(".droga5").val();
            var val6 = $(".droga6").val();
            
            $(".droga1 option").each(function(){
                if ((val2 == $(this).val() || val3 == $(this).val() || val4 == $(this).val() || val5 == $(this).val() || val6 == $(this).val()) && $(this).val() != "") {
                        $(this).attr("disabled", "disabled");
                } else {
                        $(this).removeAttr("disabled");
                }
            });

            $(".droga3 option").each(function(){
                    if ((val1 == $(this).val() ||val2 == $(this).val() || val4 == $(this).val() || val5 == $(this).val() || val6 == $(this).val()) && $(this).val() != "") {
                            $(this).attr("disabled", "disabled");
                    } else {
                            $(this).removeAttr("disabled");
                    }
            });

            $(".droga4 option").each(function(){
                    if ((val1 == $(this).val() ||val3 == $(this).val() || $(this).val() == val2 || val5 == $(this).val() || $(this).val() == val6) && $(this).val() != "") {
                            $(this).attr("disabled", "disabled");
                    } else {
                            $(this).removeAttr("disabled");
                    }
            });

            $(".droga5 option").each(function(){
                    if ((val1 == $(this).val() ||val3 == $(this).val() || $(this).val() == val2 || val4 == $(this).val() || $(this).val() == val6) && $(this).val() != "") {
                            $(this).attr("disabled", "disabled");
                    } else {
                            $(this).removeAttr("disabled");
                    }
            });

            $(".droga6 option").each(function(){
                    if ((val1 == $(this).val() ||val3 == $(this).val() || $(this).val() == val2 || val4 == $(this).val() || $(this).val() == val5) && $(this).val() != "") {
                            $(this).attr("disabled", "disabled");
                    } else {
                            $(this).removeAttr("disabled");
                    }
            });
    });
    $(".droga2").change();

    $(".droga3").change(function() {
        var val1 = $(".droga1").val();
            var val3 = $(this).val();
            var val2 = $(".droga2").val();
            var val4 = $(".droga4").val();
            var val5 = $(".droga5").val();
            var val6 = $(".droga6").val();
            
            $(".droga1 option").each(function(){
                if ((val2 == $(this).val() || val3 == $(this).val() || val4 == $(this).val() || val5 == $(this).val() || val6 == $(this).val()) && $(this).val() != "") {
                        $(this).attr("disabled", "disabled");
                } else {
                        $(this).removeAttr("disabled");
                }
            });

            $(".droga2 option").each(function(){
                    if ((val1 == $(this).val() ||val3 == $(this).val() || val4 == $(this).val() || val5 == $(this).val() || val6 == $(this).val()) && $(this).val() != "") {
                            $(this).attr("disabled", "disabled");
                    } else {
                            $(this).removeAttr("disabled");
                    }
            });

            $(".droga4 option").each(function(){
                    if ((val1 == $(this).val() ||val3 == $(this).val() || $(this).val() == val2 || val5 == $(this).val() || $(this).val() == val6) && $(this).val() != "") {
                            $(this).attr("disabled", "disabled");
                    } else {
                            $(this).removeAttr("disabled");
                    }
            });

            $(".droga5 option").each(function(){
                    if ((val1 == $(this).val() ||val3 == $(this).val() || $(this).val() == val2 || val4 == $(this).val() || $(this).val() == val6) && $(this).val() != "") {
                            $(this).attr("disabled", "disabled");
                    } else {
                            $(this).removeAttr("disabled");
                    }
            });

            $(".droga6 option").each(function(){
                    if ((val1 == $(this).val() ||val3 == $(this).val() || $(this).val() == val2 || val4 == $(this).val() || $(this).val() == val5) && $(this).val() != "") {
                            $(this).attr("disabled", "disabled");
                    } else {
                            $(this).removeAttr("disabled");
                    }
            });
    });
    $(".droga3").change();

    $(".droga4").change(function() {
        var val1 = $(".droga1").val();
            var val4 = $(this).val();
            var val2 = $(".droga2").val();
            var val3 = $(".droga3").val();
            var val5 = $(".droga5").val();
            var val6 = $(".droga6").val();
            
            $(".droga1 option").each(function(){
                if ((val2 == $(this).val() || val3 == $(this).val() || val4 == $(this).val() || val5 == $(this).val() || val6 == $(this).val()) && $(this).val() != "") {
                        $(this).attr("disabled", "disabled");
                } else {
                        $(this).removeAttr("disabled");
                }
            });

            $(".droga2 option").each(function(){
                    if ((val1 == $(this).val() ||val3 == $(this).val() || val4 == $(this).val() || val5 == $(this).val() || val6 == $(this).val()) && $(this).val() != "") {
                            $(this).attr("disabled", "disabled");
                    } else {
                            $(this).removeAttr("disabled");
                    }
            });

            $(".droga3 option").each(function(){
                    if ((val1 == $(this).val() ||val4 == $(this).val() || $(this).val() == val2 || val5 == $(this).val() || $(this).val() == val6) && $(this).val() != "") {
                            $(this).attr("disabled", "disabled");
                    } else {
                            $(this).removeAttr("disabled");
                    }
            });

            $(".droga5 option").each(function(){
                    if ((val1 == $(this).val() ||val3 == $(this).val() || $(this).val() == val2 || val4 == $(this).val() || $(this).val() == val6) && $(this).val() != "") {
                            $(this).attr("disabled", "disabled");
                    } else {
                            $(this).removeAttr("disabled");
                    }
            });

            $(".droga6 option").each(function(){
                    if ((val1 == $(this).val() ||val3 == $(this).val() || $(this).val() == val2 || val4 == $(this).val() || $(this).val() == val5) && $(this).val() != "") {
                            $(this).attr("disabled", "disabled");
                    } else {
                            $(this).removeAttr("disabled");
                    }
            });
    });
    $(".droga4").change();

    $(".droga5").change(function() {
        var val1 = $(".droga1").val();
            var val5 = $(this).val();
            var val2 = $(".droga2").val();
            var val3 = $(".droga3").val();
            var val4 = $(".droga4").val();
            var val6 = $(".droga6").val();
            
            $(".droga1 option").each(function(){
                if ((val2 == $(this).val() || val3 == $(this).val() || val4 == $(this).val() || val5 == $(this).val() || val6 == $(this).val()) && $(this).val() != "") {
                        $(this).attr("disabled", "disabled");
                } else {
                        $(this).removeAttr("disabled");
                }
            });

            $(".droga2 option").each(function(){
                    if ((val1 == $(this).val() ||val3 == $(this).val() || val4 == $(this).val() || val5 == $(this).val() || val6 == $(this).val()) && $(this).val() != "") {
                            $(this).attr("disabled", "disabled");
                    } else {
                            $(this).removeAttr("disabled");
                    }
            });

            $(".droga3 option").each(function(){
                    if ((val1 == $(this).val() ||val4 == $(this).val() || $(this).val() == val2 || val5 == $(this).val() || $(this).val() == val6) && $(this).val() != "") {
                            $(this).attr("disabled", "disabled");
                    } else {
                            $(this).removeAttr("disabled");
                    }
            });

            $(".droga4 option").each(function(){
                    if ((val1 == $(this).val() ||val2 == $(this).val() || $(this).val() == val3 || val5 == $(this).val() || $(this).val() == val6) && $(this).val() != "") {
                            $(this).attr("disabled", "disabled");
                    } else {
                            $(this).removeAttr("disabled");
                    }
            });

            $(".droga6 option").each(function(){
                    if ((val1 == $(this).val() ||val3 == $(this).val() || $(this).val() == val2 || val4 == $(this).val() || $(this).val() == val5) && $(this).val() != "") {
                            $(this).attr("disabled", "disabled");
                    } else {
                            $(this).removeAttr("disabled");
                    }
            });
    });
    $(".droga5").change();

    $(".droga6").change(function() {
        var val1 = $(".droga1").val();
            var val6 = $(this).val();
            var val2 = $(".droga2").val();
            var val3 = $(".droga3").val();
            var val4 = $(".droga4").val();
            var val5 = $(".droga5").val();
            $(".droga1 option").each(function(){
                if ((val2 == $(this).val() || val3 == $(this).val() || val4 == $(this).val() || val5 == $(this).val() || val6 == $(this).val()) && $(this).val() != "") {
                        $(this).attr("disabled", "disabled");
                } else {
                        $(this).removeAttr("disabled");
                }
            });

            $(".droga2 option").each(function(){
                    if ((val1 == $(this).val() ||val3 == $(this).val() || val4 == $(this).val() || val5 == $(this).val() || val6 == $(this).val()) && $(this).val() != "") {
                            $(this).attr("disabled", "disabled");
                    } else {
                            $(this).removeAttr("disabled");
                    }
            });

            $(".droga3 option").each(function(){
                    if ((val1 == $(this).val() ||val4 == $(this).val() || $(this).val() == val2 || val5 == $(this).val() || $(this).val() == val6) && $(this).val() != "") {
                            $(this).attr("disabled", "disabled");
                    } else {
                            $(this).removeAttr("disabled");
                    }
            });

            $(".droga4 option").each(function(){
                    if ((val1 == $(this).val() ||val2 == $(this).val() || $(this).val() == val3 || val5 == $(this).val() || $(this).val() == val6) && $(this).val() != "") {
                            $(this).attr("disabled", "disabled");
                    } else {
                            $(this).removeAttr("disabled");
                    }
            });

            $(".droga5 option").each(function(){
                    if ((val1 == $(this).val() ||val3 == $(this).val() || $(this).val() == val2 || val4 == $(this).val() || $(this).val() == val6) && $(this).val() != "") {
                            $(this).attr("disabled", "disabled");
                    } else {
                            $(this).removeAttr("disabled");
                    }
            });
    });
    $(".droga6").change();
});