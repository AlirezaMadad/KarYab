//------------------------------------------------------------------------------
// <auto-generated>
//     This code was generated from a template.
//
//     Manual changes to this file may cause unexpected behavior in your application.
//     Manual changes to this file will be overwritten if the code is regenerated.
// </auto-generated>
//------------------------------------------------------------------------------

namespace KarYab.Models
{
    using System;
    using System.Collections.Generic;
    
    public partial class POINT
    {
        public POINT()
        {
            this.OWNERSPOINTS = new HashSet<OWNERSPOINT>();
        }
    
        public long ID { get; set; }
        public long POINTSAMOUNT { get; set; }
    
        public virtual ICollection<OWNERSPOINT> OWNERSPOINTS { get; set; }
    }
}
