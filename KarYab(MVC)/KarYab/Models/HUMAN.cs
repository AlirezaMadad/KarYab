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
    
    public partial class HUMAN
    {
        public HUMAN()
        {
            this.IMAGES = new HashSet<IMAGE>();
            this.OWNERSADRESES = new HashSet<OWNERSADRES>();
            this.OWNERSCELLPHONES = new HashSet<OWNERSCELLPHONE>();
            this.OWNERSCONTRACTS = new HashSet<OWNERSCONTRACT>();
            this.OWNERSCONTRACTTYPES = new HashSet<OWNERSCONTRACTTYPE>();
            this.OWNERSEDUCATIONS = new HashSet<OWNERSEDUCATION>();
            this.OWNERSEXPERIENCES = new HashSet<OWNERSEXPERIENCE>();
            this.OWNERSMAILS = new HashSet<OWNERSMAIL>();
            this.OWNERSPHOES = new HashSet<OWNERSPHO>();
            this.OWNERSPOINTS = new HashSet<OWNERSPOINT>();
            this.OWNERSSALARIES = new HashSet<OWNERSSALARy>();
            this.OWNERSSKILLS = new HashSet<OWNERSSKILL>();
            this.PASSWORDS = new HashSet<PASSWORD>();
        }
    
        public long ID { get; set; }
        public string NAME { get; set; }
        public byte GENDER { get; set; }
        public short USAGE { get; set; }
    
        public virtual ICollection<IMAGE> IMAGES { get; set; }
        public virtual ICollection<OWNERSADRES> OWNERSADRESES { get; set; }
        public virtual ICollection<OWNERSCELLPHONE> OWNERSCELLPHONES { get; set; }
        public virtual ICollection<OWNERSCONTRACT> OWNERSCONTRACTS { get; set; }
        public virtual ICollection<OWNERSCONTRACTTYPE> OWNERSCONTRACTTYPES { get; set; }
        public virtual ICollection<OWNERSEDUCATION> OWNERSEDUCATIONS { get; set; }
        public virtual ICollection<OWNERSEXPERIENCE> OWNERSEXPERIENCES { get; set; }
        public virtual ICollection<OWNERSMAIL> OWNERSMAILS { get; set; }
        public virtual ICollection<OWNERSPHO> OWNERSPHOES { get; set; }
        public virtual ICollection<OWNERSPOINT> OWNERSPOINTS { get; set; }
        public virtual ICollection<OWNERSSALARy> OWNERSSALARIES { get; set; }
        public virtual ICollection<OWNERSSKILL> OWNERSSKILLS { get; set; }
        public virtual ICollection<PASSWORD> PASSWORDS { get; set; }
    }
}
