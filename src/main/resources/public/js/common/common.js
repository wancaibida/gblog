(function ($)
{
    window.common = {};

    common.listToTree = function (list, idName, pidName)
    {
        var map = {};
        $.each(list, function (index, obj)
        {
            var id = obj[idName];
            var pid = obj[pidName];
            map[id] = $.extend(map[id], obj);

            if (pid)
            {
                var parentObj = map[pid];
                if (!parentObj)
                {
                    parentObj = {children : []};
                    map[pid] = parentObj;
                }

                if (!parentObj.children)
                {
                    parentObj.children = [];
                }

                parentObj.children.push(obj);
            }
        });

        $.each(map, function (key, val)
        {
            if (!(!val[pidName] || val.children))
            {
                delete map[key];
            }
        });

        return map;
    };

})(jQuery);